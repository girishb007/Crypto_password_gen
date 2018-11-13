package com.example.android.encrypt_decrypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText entertext , passwordtxt ;
    TextView textView2 ;
    Button button , button2 ;
    String outputString ;
    String AES="AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        entertext =(EditText)findViewById( R.id.entertext );
        passwordtxt = (EditText)findViewById( R.id.passwordtxt );

         textView2 = (TextView)findViewById( R.id.textView2 );

         button = (Button)findViewById( R.id.button );
         button2 = (Button)findViewById( R.id.button2 );

         button.setOnClickListener( new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 try {
                     outputString = encrpyt (entertext.getText().toString(), passwordtxt.getText().toString());
                     textView2.setText( outputString );
                 } catch (Exception e) {
                     e.printStackTrace();
                 }

             }
         } );


         button2.setOnClickListener( new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 try {
                     outputString = decrypt(outputString,passwordtxt.getText().toString());
                 } catch (Exception e) {
                     Toast.makeText( MainActivity.this,"Wrong password can't decrypt" , Toast.LENGTH_SHORT ).show();
                     e.printStackTrace();
                 }
                 textView2.setText( outputString );
             }
         } );







    }

    private String decrypt(String outputString, String password) throws Exception {
        SecretKeySpec key = generateKey(password);

        Cipher c = Cipher.getInstance( AES );
        c.init( Cipher.DECRYPT_MODE, key );
        byte[] decodevalue= Base64.decode( outputString ,Base64.DEFAULT );
        byte[] decvalue = c.doFinal(decodevalue);
        String decryptedvalue = new String( decvalue );
        return decryptedvalue ;


    }

    private String encrpyt(String daata, String password) throws Exception {
        SecretKeySpec key = generateKey(password);

        Cipher c = Cipher.getInstance( AES );
        c.init( Cipher.ENCRYPT_MODE, key );
        byte[] encvalue = c.doFinal( daata.getBytes());
        String encrpytedvalues = Base64.encodeToString( encvalue , Base64.DEFAULT );
        return encrpytedvalues;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
        byte[] bytes =password.getBytes("UTF-8");

        digest.update( bytes, 0, bytes.length );
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec =new SecretKeySpec( key , "AES" );
        return secretKeySpec ;

    }
}
