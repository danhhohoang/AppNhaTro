package com.example.appnhatro.tool;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.LandLordAddNewPost;
import com.example.appnhatro.Activity.LandLordUpdatePostActivity;
import com.example.appnhatro.Activity.PostActivity;
import com.example.appnhatro.Models.BitMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class ConverImage {
    public void layAnh(BitMap bitMap) {
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference(bitMap.getTenHinh());
        try {
            final File file = File.createTempFile(bitMap.getTenHinh().substring(0, bitMap.getTenHinh().length() - 4), "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitMap.setHinh(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void docAnhAddNewPost(Uri[] filePath, Context context, String tenHinh, String tenHinh1, String tenHinh2) {
        final Integer[] dem = {0};
        for (int i = 0; i < filePath.length; i++) {
            if (filePath[i] != null) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                StorageReference ref;
                if (i == 0) {
                     ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh);
                }else if (i == 1) {
                     ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh1);
                }else {
                     ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh2);
                }
                ref.putFile(filePath[i])
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                if (dem[0] == filePath.length - 1) {
                                    ((LandLordAddNewPost) context).finish();
                                }
                                dem[0] = dem[0] +1;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");

                            }
                        });
            }
        }
    }

    public void docAnhUpdatePost(Uri[] filePath, Context context, String tenHinh,String tenHinh1,String tenHinh2) {
        final Integer[] dem = {0};
        for (int i = 0; i < filePath.length; i++) {
            if (filePath[i] != null) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                StorageReference ref;
                if (i == 0) {
                    ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh);
                }else if (i == 1) {
                    ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh1);
                }else {
                    ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh2);
                }
                ref.putFile(filePath[i])
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                if (dem[0] == 2) {
                                    ((LandLordUpdatePostActivity) context).finish();
                                }
                                dem[0] = dem[0] +1;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");

                            }
                        });
            }else {
                dem[0] = dem[0] +1;;
            }
        }
    }
    public void docAnhAddNewPostOghep(Uri[] filePath, Context context, String tenHinh, String tenHinh1, String tenHinh2) {
        final Integer[] dem = {0};
        for (int i = 0; i < filePath.length; i++) {
            if (filePath[i] != null) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                StorageReference ref;
                if (i == 0) {
                    ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh);
                }else if (i == 1) {
                    ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh1);
                }else {
                    ref = FirebaseStorage.getInstance().getReference().child("images/post/" + tenHinh2);
                }
                ref.putFile(filePath[i])
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                if (dem[0] == filePath.length - 1) {
                                    ((PostActivity) context).finish();
                                }
                                dem[0] = dem[0] +1;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");

                            }
                        });
            }
        }
    }
}
