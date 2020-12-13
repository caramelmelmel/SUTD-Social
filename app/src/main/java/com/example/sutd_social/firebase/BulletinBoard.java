package com.example.sutd_social.firebase;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.sutd_social.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class BulletinBoard {
    private static final BulletinBoard ourInstance = new BulletinBoard();
    private static final String TAG = "BulletinBoard";
    private static final HashMap<String, Bulletin> bulletinBoard = new HashMap<>();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // use this with format() or parse()
    private static DatabaseReference bulletinRef;
    private static StorageReference bulletinImgRef;

    private BulletinBoard() {
        Log.d(TAG, "Initialising BulletinBoard");

        // Set up Storage Instance
        bulletinImgRef = FirebaseStorage.getInstance().getReference();
        bulletinImgRef = bulletinImgRef.child("/BulletinBoardPic");

        //Getting Firebase Instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        bulletinRef = database.getReference();

        //Getting reference to "BulletinBoard" node
        bulletinRef = bulletinRef.child("BulletinBoard");

        //Adding eventListener to reference
        bulletinRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: Adding " + snapshot.getKey());
                bulletinBoard.put(snapshot.getKey(), snapshot.getValue(Bulletin.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Same as onChildAdded
                Log.d(TAG, "onChildChanged: " + snapshot.getKey());
                bulletinBoard.put(snapshot.getKey(), snapshot.getValue(Bulletin.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                bulletinBoard.remove(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e(TAG, "onChildMoved: " + snapshot.getKey() + snapshot.getValue(Bulletin.class).toString());
                Log.e(TAG, "onChildMoved: You should not be triggering this method!!");
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Something went wrong! Error:" + databaseError.getMessage());
            }
        });

    }

    public static BulletinBoard getInstance() {
        return ourInstance;
    }

    // Get the whole HashMap of Bulletins
    public static HashMap<String, Bulletin> getBulletin() {
        return bulletinBoard;
    }

    // Get the Bulletin with the specified id
    public static Bulletin getBulletin(String id) {
        return bulletinBoard.get(id);
    }

    // Use this to add new Bulletin or update Bulletin
    public static void addBulletin(String id, Bulletin bulletin) {
        bulletinRef.child(id).setValue(bulletin);
        bulletinBoard.put(id, bulletin);  // TODO: unsafe operation. Add failure listener
    }

    // Alternatively:
    public static void addBulletin(String id, String title, String description, String fifthRow, String image, String url, String expiryDate) {
        // If the user never specify any field, the default is "" (same goes for image)
        Bulletin newBulletin = new Bulletin(title, description, fifthRow, image, url, expiryDate);

        addBulletin(id, newBulletin);
    }

    public static void addBulletin(String id, String title, String description, String fifthRow, Uri image, String url, String expiryDate) {
        // Similar method as above, except that image is provided
        Bulletin bulletin = new Bulletin(title, description, fifthRow, "", url, expiryDate);
        addImage(id, bulletin, image);
    }

    public static void addImage(final String id, final Bulletin bulletin, Uri image) {
        // https://firebase.google.com/docs/storage/android/upload-files
        // Point to the person's image in jpg format
        String fileId = id + ".jpg";
        final StorageReference imgRef = bulletinImgRef.child(fileId);
        Log.d(TAG, "Writing data: " + fileId);
        Log.d(TAG, "addImage: " + image.toString());

        // Upload image
        UploadTask uploadTask = imgRef.putFile(image);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "onSuccess: Image uploaded");
                // Success!
                // Get file url to upload url onto firebase
                imgRef.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri imageUrl = uri;

                        // Upload url to firebase
                        bulletin.image = imageUrl.toString();
                        addBulletin(id, bulletin);
                    }
                });
            }
        });
    }

    public static void displayImage(Activity context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.default_bulletin).into(imageView);
    }

    // Date is stored as String in Firebase for convenience
    // Helper methods for Date-String conversion
    public static String dateFormat(Date date) {
        return dateFormat.format(date);
    }

    public static Date dateParse(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, "dateParse: An error has occurred while parsing the date");
            e.printStackTrace();
            return null;
        }
    }
}

/*

Testing code:

    public void testing(View v) {
        Log.d(TAG, "testing: BulletinBoard addBulletin");

        BulletinBoard.addBulletin("003", "first Bullet", "Hello, welcome!",
                                    "SOAR", "https://amIdone.com", "", "2020-11-30");

        Bulletin bulletin = BulletinBoard.getBulletin("003");
        try {
            Date a = Bulletin.dateFormat.parse(bulletin.expiryDate);
            Log.d(TAG, "testing: " + a.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

*/
