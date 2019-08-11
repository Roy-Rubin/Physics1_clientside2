package com.sipl.physics1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitActivity extends AppCompatActivity {

    Button buttonToCamera;
    Button buttonToMainActivity;

    private Socket mSocket;
    private DataInputStream input = null;                   //maybe this can be deleted later
    private DataOutputStream output_to_server = null;       //maybe this can be deleted later

    ImageView imgShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        // init view
        imgShow = (ImageView) findViewById(R.id.imgShow);

        // init buttons
        buttonToCamera = (Button) findViewById(R.id.button_to_camera);
        buttonToMainActivity = (Button) findViewById(R.id.button_to_main_activity);

        buttonToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        buttonToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                mSocket.emit(CLIENT_SEND_IMAGE, bytes);
                mSocket.getOutputStream();
                mSocket.getInputStream();

                PrintWriter printWriter;
                */

//                createInternetConnection();  //this is instead of all of the code above
                //
//                postData(picture_received);
                //
                Log.d(TAG, "entering function - postData_notOnUIThread \n");
                //postData_notOnUIThread(picture_received);
                postData_2_notOnUIThread(picture_received);
                Log.d(TAG, "returning from function - postData_notOnUIThread \n");
                Toast.makeText(InitActivity.this,"calculating...", Toast.LENGTH_SHORT).show();


                //process data !!!! //todo: process the data, get back a JSON object. then, throw the JSON object to the next activity [meanwhile - do a loading circle ?]

                //and then:

                //todo: this comes later
                /*
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                //todo: put the data in the intent !!!
                startActivity(intent);
                */

            }
        });

        /*// init socket
        // !!!!
        Log.d(TAG, "begin internet connection phase");
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("http://localhost"); //todo: ?!?!?!?!?!?!? <---------------------------------------------------------
            // note - above i could have just sent "null", or "com.sipl.physics1"  //todo: note this shit.
            Log.d(TAG, "getting inetAddress. address is:  " + inetAddress);
            System.out.println(inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int port = 3000;  //todo: ?!?!?!?!?!?!? <---------------------------------------------------------
        SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
        mSocket = new Socket();
        try {
            //binding the  socket with the inetAddress and port number
            mSocket.bind(socketAddress);
            //connect() method connects the specified socket to the server
            mSocket.connect(socketAddress);
            //
            System.out.println("Connected");
            Log.d(TAG, "connection established [allegedly]");
            // ' sends output to the socket ' -
            output_to_server = new DataOutputStream(mSocket.getOutputStream());         // might not be used later.. check later.
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
*/



//        createInternetConnection();  //this is instead of all of the code above

    }



    private void takePicture(){
        Log.d(TAG, "entered function - takePicture \n");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    Bitmap picture_received = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "entered function - onActivityResult \n");
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            picture_received = (Bitmap) data.getExtras().get("data");
            imgShow.setImageBitmap(picture_received);
        }
    }


/*
    byte[] bytes = null;
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = resize(bitmap, 100, 100);
            bytes = getByteArrayFromBitmap(bitmap);
            //mSocket.emit(CLIENT_SEND_IMAGE, bytes);
        }
    }*/

    public byte[] getByteArrayFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);  //todo: NOTE - i changed the type from png to JPEG
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void createInternetConnection() {
        //
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // code for the thread to perform starts here.
                    Log.d(TAG, "entered function - thread->run \n");
                    //
                    Log.d(TAG, "begin internet connection phase");
                    InetAddress inetAddress = null;
                    try {
                        inetAddress = InetAddress.getByName("http://132.68.58.72"); //todo: ?!?!?!?!?!?!? <---------------------------------------------------------
                        // note - above i could have just sent "null", or "com.sipl.physics1"  //todo: note this shit.
                        Log.d(TAG, "getting inetAddress. address is:  " + inetAddress);
                        System.out.println(inetAddress);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    int port = 1936;  //todo: ?!?!?!?!?!?!? <---------------------------------------------------------
                    SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
                    mSocket = new Socket();
                    try {
                        //binding the  socket with the inetAddress and port number
                        mSocket.bind(socketAddress);                                //todo: - the "bind" might not be needed. also, i saw somewhere that they didn't use "connect", only in the "Socket()"'s CTOR
                        //connect() method connects the specified socket to the server
                        mSocket.connect(socketAddress);
                        //
                        System.out.println("Connected");
                        Log.d(TAG, "connection established [allegedly]");
                        // ' sends output to the socket ' -
                        output_to_server = new DataOutputStream(mSocket.getOutputStream());         // might not be used later.. check later.
                        //
                    } catch (IOException e) {
                        e.printStackTrace();
                    }





                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static void postData(Bitmap imageToSend) {
        try
        {
            URL url = new URL("http://132.68.58.72:1936/upload-image");     ///todo: CHANGE TO LOCAL HOST !
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");

            httpURLConnection.setReadTimeout(35000);
            httpURLConnection.setConnectTimeout(35000);

            //todo: NOT SURE THESE 2 LINES ARE NEEDED. THESE 2 LINES WERE ADDED LATER ON
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data");
            httpURLConnection.setFixedLengthStreamingMode(1024);

            // directly let .compress write binary image data
            // to the output-stream
            OutputStream outputStream = httpURLConnection.getOutputStream();
            imageToSend.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            outputStream.flush();
            outputStream.close();

            // the whole next section is for the response, and the response code.
            // write response code
            System.out.println("Response Code: " + httpURLConnection.getResponseCode());
            //write response msg
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            Log.d(TAG, "sfsd");                                              //TODO: change the log here ....
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null)
                stringBuilder.append(line).append("\n");
            responseStreamReader.close();
            String response = stringBuilder.toString();
            System.out.println(response);

            //
            httpURLConnection.disconnect();
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    private void postData_notOnUIThread(final Bitmap imageToSend) {
        //
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // code for the thread to perform starts here.
                    Log.d(TAG, " -----------------\n entered function - thread->run \n -----------------");
                    //
                    Log.d(TAG, "begin internet connection phase");

                    //
                    try
                    {
                        URL url = new URL("http://132.68.58.72:1936/upload-image");     ///todo: CHANGE TO LOCAL HOST !
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        Log.d(TAG, "allegedly the connection has been established. now making some adjustments.");

                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
//                        httpURLConnection.setRequestProperty("Cache-Control", "no-cache"); //todo: check if this is needed

                        httpURLConnection.setReadTimeout(100000); //35000 = 35 secs //100000 = 100 secs
                        httpURLConnection.setConnectTimeout(100000); //35000 = 35 secs //100000 = 100 secs
                        httpURLConnection.setChunkedStreamingMode(0);

                        //todo: NOT SURE THESE 2 LINES ARE NEEDED. THESE 2 LINES WERE ADDED LATER ON
//                        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data");
//                        httpURLConnection.setFixedLengthStreamingMode(8192);                    //<----todo: NOTE: error "java.net.ProtocolException: expected 1024 bytes but received 8192" i changed this to the right size

                        // directly let .compress write binary image data
                        // to the output-stream
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        OutputStream outputStreamBuffered = new BufferedOutputStream(httpURLConnection.getOutputStream());

                        Log.d(TAG, "about to send image.");
//                        imageToSend.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        //trying with a copy
                        Bitmap copy_of_image = Bitmap.createBitmap(imageToSend);

//                        copy_of_image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                        copy_of_image.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamBuffered);

                        copy_of_image = resize(copy_of_image, 100, 100);
                        byte[] byte_arr = getByteArrayFromBitmap(copy_of_image);

                        outputStreamBuffered.write(byte_arr);


//                        outputStream.flush();
//                        outputStream.close();

                        outputStreamBuffered.flush();
                        outputStreamBuffered.close();



                        Log.d(TAG, "image (allegedly) sent");

                        // the whole next section is for the response, and the response code.
                        // write response code
//                        System.out.println("Response Code: " + httpURLConnection.getResponseCode());  //todo: note i commented these 2 lines. i supoect nothing is being sent here ...
//                        Log.d(TAG, "Response Code: " + httpURLConnection.getResponseCode());

/*                        //write response msg
                        InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        Log.d(TAG, "sfsd");                                              //TODO: change the log here ....
                        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = "";
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = responseStreamReader.readLine()) != null)
                            stringBuilder.append(line).append("\n");
                        responseStreamReader.close();
                        String response = stringBuilder.toString();
                        System.out.println(response);
                        */

                        //
                        Log.d(TAG, " -----------------\n supposedly everything is ok if we got here, closing connection now \n -----------------");
//                        Log.d(TAG, "the response was: " + response);
                        httpURLConnection.disconnect();
                    }
                    catch(MalformedURLException e) {
                        e.printStackTrace();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }





                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private void postData_2_notOnUIThread(final Bitmap imageToSend) {
        //
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // code for the thread to perform starts here.
                    Log.d(TAG, " -----------------\n entered function - thread->run \n -----------------");
                    //
                    Log.d(TAG, "begin internet connection phase");

                    //
                    //the client needs to know:
                    //1. ip address of the system that the server is running on
                    //2. port number of that server program
                    //
                    try { //try to connect to a server

//                        Socket socket = new Socket("127.0.0.1", 3000);

                        Socket socket = new Socket("132.68.58.72", 1936);  //"132.68.58.72"  or "http://132.68.58.72"

                        System.out.println("Client connected");
                        Log.d(TAG, "Client connected");

                        //

                        // send the screenshot to the client
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        //System.out.println("After Datastream output obj"); //change this later


/*
                        Log.d(TAG, "rcvscrnshot: outside while");
                        int n;
                        int i = 0;
                        FileInputStream inStream = new FileInputStream(file);  //this will get an error. its supposed to be the image
                        byte[] buffer = new byte[4096];


                        while((n = inStream.read(buffer)) != -1) {
                            System.out.println("In while");
                            i++;
                            System.out.println(i + ". Byte[" + n + "]");
                            dataOutputStream.write(buffer, 0, n);
                            dataOutputStream.flush();

                        }
                        Log.d(TAG, "rcvscrnshot: after while");

                        */

                        //now im trying my own:

                        Log.d(TAG, "about to send image.");
//                        imageToSend.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        //trying with a copy
                        Bitmap copy_of_image = Bitmap.createBitmap(imageToSend);

//                        copy_of_image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                        copy_of_image.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamBuffered);

                        //copy_of_image = resize(copy_of_image, 100, 100);  //WARNING: test - without the resize
                        byte[] byte_arr = getByteArrayFromBitmap(copy_of_image);

                        dataOutputStream.write(byte_arr);
                        dataOutputStream.flush();
                        dataOutputStream.close();


                        //System.out.println("After Datastream output obj"); //change this later


                    } catch (Exception e) {
                        // todo: print error

                        System.out.println("could not connect to server");
                    }





                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////



    ////
    private static final String TAG = "Physics1";
    private boolean finished_taking_pictures = false;


    private final String CLIENT_SEND_IMAGE = "CLIENT_SEND_IMAGE";
    private final String SERVER_SEND_IMAGE = "SERVER_SEND_IMAGE";
    private final String CLIENT_SEND_REQUEST = "CLIENT_SEND_REQUEST";
    private final String CLIENT_SEND_REQUEST_SOUND = "CLIENT_SEND_REQUEST_SOUND";
    private final int REQUEST_TAKE_PHOTO = 123;
    private final int REQUEST_CHOOSE_PHOTO = 321;


/*
    Emitter.Listener onNewImage, onNewSound;

    {
        try {
            mSocket = IO.socket("http://10.0.0.3:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        onNewImage = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                handleNewImage(args[0]);
            }
        };
        onNewSound = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                handleNewSound(args[0]);
            }
        };
    }
*/



/*
    private String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Physics1_JPEG_" + timeStamp + ".jpg";
        //tst
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SIPL_Physics1");
        //end tst

*//*        File image = File.createTempFile(
                imageFileName,  *//**//* prefix *//**//*
                ".jpg",         *//**//* suffix *//**//*
                storageDir      *//**//* directory *//**//*
        );

        currentPhotoPath = image.getAbsolutePath();*//*

        File new_image = new File(storageDir + File.separator + imageFileName);
        currentPhotoPath = new_image.getAbsolutePath();

        // Save a file: path for use with ACTION_VIEW intents
//        return image;
        return new_image;
    }




    /////
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //static final int REQUEST_TAKE_PHOTO = 1;

    //my first version
    private void dispatchTakePictureIntent() {
        Log.d(TAG, "entered 'dispatchTakePictureIntent' before taking the pictures");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            //in the background - go and see function onActivityResult
            Log.d(TAG, "now starting 'startActivityForResult' before taking the pictures");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


        } else {
            Log.d(TAG, "[Roy's Debugging message] did not enter if condition");

        }
    }
    //*/

/*    private void dispatchTakePictureIntent() {
        Log.d(TAG, "entered 'dispatchTakePictureIntent' before taking the pictures");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //old
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.sipl.physics1.fileprovider",
                        photoFile);
                // new
                Uri photoURI2 = Uri.fromFile(photoFile);  //TODO: this is the second try for this thing
                //old
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //new
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);  //TODO !!! this is supposed to be the line that gives the camera intent the address for the bitmap

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                //
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(photoURI2);
                this.sendBroadcast(mediaScanIntent);
            }
        }
    }*/


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "entered onActivityResult after taking the picture");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(TAG, "entered if condition -> meaning: requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ");
            // get the bitmap
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    Log.d(TAG, "data acquisition - succeeded (= imageBitmap was created)");
                } else {
                    Log.d(TAG, "data acquisition - failed (= imageBitmap was not created)");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO: might need to convert data to bytes in order to save the image.
            // doing it now
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] byteArray_of_bitmap = stream.toByteArray();
//            imageBitmap.recycle(); // TODO: ?? is this needed ?
            if (byteArray_of_bitmap != null) {
                Log.d(TAG, "conversion from bitmap to byte array - succeeded");
            } else {
                Log.d(TAG, "conversion from bitmap to byte array - failed");
            }


            // create the photo file (just the path - its empty for now)
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                // Error occurred while creating the File
                Log.d(TAG, "[Roy's Debugging message] Couldn't create photo: " + e.getMessage());
                e.printStackTrace();
            }

            if (photoFile != null) {
                Log.d(TAG, "photo file creation - succeeded");
            } else {
                Log.d(TAG, "photo file creation - failed");
            }


            // save the bitmap into the photo path created just above me.
            try {

                FileOutputStream fos = new FileOutputStream(photoFile);

                fos.write(byteArray_of_bitmap);  //note: was previously (before changin bitmap to byte array) "imageBitmap"
                //alternative to try later
                // instead of write:
//                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);  //TODO: format is PNG !?

                fos.close();

            } catch (FileNotFoundException e) {
                Log.d(TAG, "[Roy's Debugging message] File not found: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, "[Roy's Debugging message] Error accessing file: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.d(TAG, "[Roy's Debugging message] other error:  " + e.getMessage());
                e.printStackTrace();
            }

            //

            try {
//                MediaScannerConnection.scanFile(this, new String[] {currentPhotoPath}, null, null);
                MediaScannerConnection.scanFile(this, new String[] {photoFile.getPath()}, null, null);
//            MediaScannerConnection.scanFile(this, new String[] {myMediaStorageDir.toString()}, null, null);
                MediaScannerConnection.scanFile(this, new String[] {photoFile.toString()}, null, null);

            } catch (Exception e) {
                Log.d(TAG, "[Roy's Debugging message] Media scanner collapsed: " + e.getMessage());
                e.printStackTrace();
            }

            //if we got here then all is good
            finished_taking_pictures = true;

//            imageView.setImageBitmap(imageBitmap);
            String bitmap_converted_to_string_through_byteArray = null;
            try {
                bitmap_converted_to_string_through_byteArray = byteArray_of_bitmap.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }


            String ayelet = bitmap_converted_to_string_through_byteArray + "_";
            String ayelet2 = Base64.encodeToString(byteArray_of_bitmap, Base64.DEFAULT);
            String ayelet3 = null;
            try {
                ayelet3 = new String(byteArray_of_bitmap, "UTF-32");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String ayelet4 = ayelet3 + "_";
            Log.d(TAG, "end of function");
        }
    }*/






}
