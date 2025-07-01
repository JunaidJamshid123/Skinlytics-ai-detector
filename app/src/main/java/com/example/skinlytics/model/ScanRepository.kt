package com.example.skinlytics.model

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class ScanRepository {
    companion object {
        // Use 10.0.2.2 for emulator, or your PC's IP for device
        private const val BASE_URL = "http://10.0.2.2:5000" // Change to your PC IP for device
    }
    suspend fun uploadImageAndGetResult(context: Context, imageUri: Uri): ScanResult {
        val file = uriToFile(context, imageUri)
        val client = OkHttpClient()
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("image", file.name, requestBody)
            .build()
        val request = Request.Builder()
            .url("BASE_URL/predict")
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw Exception("API Error: ${response.code}")
        val json = response.body?.string() ?: "{}"
        val obj = JSONObject(json)
        return ScanResult(
            about = obj.optString("about"),
            common_symptoms = obj.optJSONArray("common_symptoms")?.let { arr ->
                List(arr.length()) { arr.getString(it) }
            } ?: emptyList(),
            disclaimer = obj.optString("disclaimer"),
            prediction = obj.optString("prediction"),
            severity = obj.optString("severity"),
            treatment_recommendations = obj.optJSONArray("treatment_recommendations")?.let { arr ->
                List(arr.length()) { arr.getString(it) }
            } ?: emptyList()
        )
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile("upload", ".jpg", context.cacheDir)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file
    }
} 