package ru.mirea.lukutin.mireaproject.ui.gallery

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.mirea.lukutin.mireaproject.R
import ru.mirea.lukutin.mireaproject.databinding.FragmentGalleryBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.nio.file.Files.createFile
import java.util.*
import java.util.jar.Manifest


private const val REQUEST_CODE = 42
private const val FILE_NAME = "photo.jpg"
private lateinit var photoFile: File

class GalleryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var btnTakePicture: Button
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        btnTakePicture = root.findViewById(R.id.btnTakePicture)
        btnTakePicture.setOnClickListener(this)
        imageView = root.findViewById(R.id.imageView)
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(FILE_NAME)
        val fileProvider = FileProvider.getUriForFile(requireActivity(),"ru.mirea.lukutin.fileprovider", photoFile)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if(takePictureIntent.resolveActivity(p0?.context?.packageManager!!) != null)
            startActivityForResult(takePictureIntent, REQUEST_CODE)
        else
            Toast.makeText(p0?.context,"Unable to open camera", Toast.LENGTH_SHORT)
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE){
            val title = "Picture".random().toString()
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            imageView.setImageBitmap(takenImage)
            MediaStore.Images.Media.insertImage(activity?.contentResolver,takenImage,title,"Photo $title")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}