//package com.example.analizo;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class AddProductToDatabase extends AppCompatActivity {
//
//    private EditText product_name,product_description,product_image;
//    DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_product);
//
//        databaseReference= FirebaseDatabase.getInstance().getReference("products");
//
//        product_name=(EditText)findViewById(R.id.product_name);
//        product_description=(EditText)findViewById(R.id.product_description);
//        product_image=(EditText) findViewById(R.id.product_image);
//    }
//
//    public void sendToDatabase(View view) {
//        addProducts();
//    }
//
//    public void addProducts(){
//        String productName=product_name.getText().toString();
//        String productDescription=product_description.getText().toString();
//        String productImage=product_image.getText().toString();
//
//        if(!TextUtils.isEmpty(productName) && !TextUtils.isEmpty(productDescription)) {
//
//            String id = databaseReference.push().getKey();
//
//            Products products = new Products(id, productName, productDescription, productImage);
//
//            databaseReference.child(id).setValue(products);
//            product_name.setText("");
//            product_description.setText("");
//            product_image.setText("");
//        }
//        else {
//            Toast.makeText(AddProductToDatabase.this, "Please Enter the Product Name, Description & Image URL.",Toast.LENGTH_LONG).show();
//        }
//    }
//}