package com.example.thoughts_socialnetwork.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
    var imageurl: String? = null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageurl = it.toString()
                callback(imageurl)
            }
        }
}

fun uploadVideo(
    uri: Uri,
    folderName: String,
    context: Context,
    callback: (String?) -> Unit
) {
    var imageurl: String? = null
    var progressDialog:ProgressDialog = ProgressDialog(context)
    progressDialog.setTitle("Uploading . . .")
    progressDialog.show()
    progressDialog.setCancelable(false)
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageurl = it.toString()
                progressDialog.dismiss()
                callback(imageurl)
            }
        }.addOnProgressListener {
            var uploadedValue: Long =( it.bytesTransferred / it.totalByteCount)*100
            progressDialog.setMessage("Uplaoded $uploadedValue%")

        }
}