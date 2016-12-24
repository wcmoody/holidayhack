package com.northpolewonderland.santagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings.Secure;
import android.support.v7.p021a.C0561d.C0558a;
import android.support.v7.p021a.C0562e;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONObject;

public class EditProfile extends C0562e {
    ProgressDialog f2420a;
    ParseUser f2421b;
    C0984a f2422c;
    boolean f2423d;
    int f2424e;
    int f2425f;

    /* renamed from: com.northpolewonderland.santagram.EditProfile.1 */
    class C08591 implements Runnable {
        final /* synthetic */ JSONObject f2408a;
        final /* synthetic */ EditProfile f2409b;

        C08591(EditProfile editProfile, JSONObject jSONObject) {
            this.f2409b = editProfile;
            this.f2408a = jSONObject;
        }

        public void run() {
            C0987b.m4776a(this.f2409b.getString(2131165213), this.f2408a);
        }
    }

    /* renamed from: com.northpolewonderland.santagram.EditProfile.2 */
    class C08602 implements GetDataCallback {
        final /* synthetic */ ImageView f2410a;
        final /* synthetic */ EditProfile f2411b;

        C08602(EditProfile editProfile, ImageView imageView) {
            this.f2411b = editProfile;
            this.f2410a = imageView;
        }

        public void done(byte[] bArr, ParseException parseException) {
            if (parseException == null) {
                Options options = new Options();
                options.inPurgeable = true;
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                if (decodeByteArray != null) {
                    this.f2410a.setImageBitmap(decodeByteArray);
                }
            }
        }
    }

    /* renamed from: com.northpolewonderland.santagram.EditProfile.3 */
    class C08613 implements OnClickListener {
        final /* synthetic */ EditProfile f2412a;

        C08613(EditProfile editProfile) {
            this.f2412a = editProfile;
        }

        public void onClick(View view) {
            this.f2412a.f2423d = true;
            Toast.makeText(this.f2412a.getApplicationContext(), "Only elves can upload images", 1).show();
        }
    }

    /* renamed from: com.northpolewonderland.santagram.EditProfile.4 */
    class C08624 implements GetDataCallback {
        final /* synthetic */ ImageView f2413a;
        final /* synthetic */ EditProfile f2414b;

        C08624(EditProfile editProfile, ImageView imageView) {
            this.f2414b = editProfile;
            this.f2413a = imageView;
        }

        public void done(byte[] bArr, ParseException parseException) {
            if (parseException == null) {
                Options options = new Options();
                options.inPurgeable = true;
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                Bitmap a = C0987b.m4772a(decodeByteArray, 1024.0f);
                if (decodeByteArray != null) {
                    this.f2413a.setImageBitmap(a);
                }
            }
        }
    }

    /* renamed from: com.northpolewonderland.santagram.EditProfile.5 */
    class C08635 implements OnClickListener {
        final /* synthetic */ EditProfile f2415a;

        C08635(EditProfile editProfile) {
            this.f2415a = editProfile;
        }

        public void onClick(View view) {
            this.f2415a.f2423d = false;
            Toast.makeText(this.f2415a.getApplicationContext(), "Only elves can upload images", 1).show();
        }
    }

    /* renamed from: com.northpolewonderland.santagram.EditProfile.6 */
    class C08656 implements OnClickListener {
        final /* synthetic */ TextView f2417a;
        final /* synthetic */ TextView f2418b;
        final /* synthetic */ EditProfile f2419c;

        /* renamed from: com.northpolewonderland.santagram.EditProfile.6.1 */
        class C08641 implements SaveCallback {
            final /* synthetic */ C08656 f2416a;

            C08641(C08656 c08656) {
                this.f2416a = c08656;
            }

            public void done(ParseException parseException) {
                if (parseException == null) {
                    this.f2416a.f2419c.f2420a.dismiss();
                    C0558a c0558a = new C0558a(this.f2416a.f2419c);
                    c0558a.m2703b((CharSequence) "Your profile has been updated!").m2694a(2131165208).m2701b(2130837592).m2700a((CharSequence) "OK", null);
                    c0558a.m2705b().show();
                    return;
                }
                Toast.makeText(this.f2416a.f2419c.getApplicationContext(), parseException.getMessage().toString(), 0).show();
                this.f2416a.f2419c.f2420a.dismiss();
            }
        }

        C08656(EditProfile editProfile, TextView textView, TextView textView2) {
            this.f2419c = editProfile;
            this.f2417a = textView;
            this.f2418b = textView2;
        }

        public void onClick(View view) {
            this.f2419c.f2420a.setMessage("Updating profile...");
            this.f2419c.f2420a.show();
            this.f2419c.f2421b.put(Configs.USER_FULLNAME, this.f2417a.getText().toString());
            this.f2419c.f2421b.put(Configs.USER_ABOUT_ME, this.f2418b.getText().toString());
            this.f2419c.f2421b.saveInBackground(new C08641(this));
        }
    }

    public EditProfile() {
        this.f2421b = ParseUser.getCurrentUser();
        this.f2422c = new C0984a(this);
        this.f2424e = 0;
        this.f2425f = 1;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            Bitmap bitmap;
            Bitmap bitmap2 = null;
            if (i == this.f2424e) {
                bitmap = (Bitmap) intent.getExtras().get("data");
            } else {
                if (i == this.f2425f) {
                    try {
                        bitmap = Media.getBitmap(getApplicationContext().getContentResolver(), intent.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                bitmap = bitmap2;
            }
            if (this.f2423d) {
                ((ImageView) findViewById(2131558535)).setImageBitmap(bitmap);
            } else {
                ((ImageView) findViewById(2131558537)).setImageBitmap(bitmap);
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        setContentView(2130968618);
        super.setRequestedOrientation(1);
        C0987b.m4774a(getApplicationContext(), getClass().getSimpleName());
        if (getString(2131165214).equals("true")) {
            Log.i(getString(2131165204), "Remote debug logging is Enabled");
            z = true;
        } else {
            Log.i(getString(2131165204), "Remote debug logging is Disabled");
            z = false;
        }
        getSupportActionBar().m2632a(true);
        getSupportActionBar().m2636b(true);
        getSupportActionBar().m2631a((CharSequence) "Edit Profile");
        this.f2420a = new ProgressDialog(this);
        this.f2420a.setTitle(2131165208);
        this.f2420a.setIndeterminate(false);
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("date", new SimpleDateFormat("yyyyMMddHHmmssZ").format(Calendar.getInstance().getTime()));
                jSONObject.put("udid", Secure.getString(getContentResolver(), "android_id"));
                jSONObject.put("debug", getClass().getCanonicalName() + ", " + getClass().getSimpleName());
                jSONObject.put("freemem", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
                new Thread(new C08591(this, jSONObject)).start();
            } catch (Exception e) {
                Log.e(getString(2131165204), "Error posting JSON debug data: " + e.getMessage());
            }
        }
        ImageView imageView = (ImageView) findViewById(2131558535);
        ParseFile parseFile = (ParseFile) this.f2421b.get(Configs.USER_AVATAR);
        if (parseFile != null) {
            parseFile.getDataInBackground(new C08602(this, imageView));
        }
        imageView.setOnClickListener(new C08613(this));
        imageView = (ImageView) findViewById(2131558537);
        parseFile = (ParseFile) this.f2421b.get(Configs.USER_COVER_IMAGE);
        if (parseFile != null) {
            parseFile.getDataInBackground(new C08624(this, imageView));
        }
        imageView.setOnClickListener(new C08635(this));
        TextView textView = (TextView) findViewById(2131558540);
        textView.setText(this.f2421b.getString(Configs.USER_FULLNAME));
        TextView textView2 = (TextView) findViewById(2131558542);
        textView2.setText(this.f2421b.getString(Configs.USER_ABOUT_ME));
        ((TextView) findViewById(2131558544)).setText(this.f2421b.getString(Configs.USER_EMAIL));
        ((Button) findViewById(2131558545)).setOnClickListener(new C08656(this, textView, textView2));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
