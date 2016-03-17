package com.gbazilio.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gbazilio.oauth2client.listeners.AccessTokenRequested;
import com.gbazilio.oauth2client.oauth2.OAuth2ClientFactory;
import com.gbazilio.oauth2client.oauth2.OAuth2LoginFragment;
import com.gbazilio.oauth2client.oauth2.clients.interfaces.OAuth2Client;
import com.gbazilio.oauth2client.oauth2.clients.interfaces.ScopeOAuth2Client;
import com.gbazilio.oauth2client.oauth2.model.Token;

public class MainActivity extends AppCompatActivity implements AccessTokenRequested {

    private static Token token;

    private OAuth2Client oauthClient;
    private Fragment oauthDialog;

    private Button buttonGithub;

    private TextView accessTokenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessTokenTextView = (TextView) findViewById(R.id.accessTokentextView);

        buttonGithub = (Button) findViewById(R.id.btnGithub);
        buttonGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oauthClient = OAuth2ClientFactory.newInstance(OAuth2ClientFactory.Provider.GITHUB);
                ((ScopeOAuth2Client) oauthClient).setScope("repo");
                oauthDialog = buildOAuthFragment(oauthClient);
            }
        });
    }

    private Fragment buildOAuthFragment(OAuth2Client oauthClient) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);

        OAuth2LoginFragment oAuth2LoginFragment = OAuth2LoginFragment.newInstance(oauthClient);
        oAuth2LoginFragment.setOnAccessTokenRequestedListener(this);
        oAuth2LoginFragment.show(fragmentTransaction, "oauth2_dialog");

        return oAuth2LoginFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAccessTokenRequested(final Token token) {

        getSupportFragmentManager().beginTransaction()
                .remove(oauthDialog).commit();

        if (token == null) {
            Log.v("User Denied", "Access Denied by user");
            accessTokenTextView.post(new Runnable() {
                @Override
                public void run() {
                    String message = "Access denied";
                    accessTokenTextView.setText(message);
                }
            });
            return;
        }

        Log.v("Provider", token.getProvider());
        Log.v("AccessToken", token.getAccessToken());
        accessTokenTextView.post(new Runnable() {
            @Override
            public void run() {
                String message = "Provider: " + token.getProvider() + " | Token: " + token.getAccessToken();
                accessTokenTextView.setText(message);
            }
        });

        this.token = token;
    }
}
