package com.br.glossario.async;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;

import com.br.glossario.interfaces.ResponseFotoListener;
import com.br.glossario.utils.FileManagement;
import com.br.glossario.utils.Session;

public class AsyncEnviaFotoGlossario extends AsyncTask<Void, Void, Void>{

	Context context;
	private File glossarioFotoDir;
	private FileManagement fileManag;
	private Session session;
	
	private File f;
	private File[] fileImage;
	
	protected ResponseFotoListener callback = null;
	
	public void setOnClickedListener(ResponseFotoListener l){
		callback = l;
	}
	
	public AsyncEnviaFotoGlossario(Context context){
		this.context = context;
		
		session = new Session(this.context);
		fileManag = new FileManagement(this.context);
		glossarioFotoDir  = fileManag.openOrCreateDirectoryExternal("glossario");
	}	
	
	@Override
	protected Void doInBackground(Void... param) {
		
		String url = "http://glossariomobile.besaba.com/glossario/upload_glossario.php";
		
		fileImage = glossarioFotoDir.listFiles();
		f = fileImage[0];
								
		File paramValue = new File(glossarioFotoDir.getPath(), f.getName());
		
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		
		HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity();
		
		entity.addPart("nfile", new FileBody((paramValue)));
		post.setEntity(entity);
		
		try {

			String response = EntityUtils.toString(client.execute(post).getEntity(), "UTF-8");			
						
			if (response.equals("Envio com Sucesso")) {						
				paramValue.delete();
												
			} else {
				
			}

		} catch (ParseException e) {
			e.printStackTrace();						
		} catch (ClientProtocolException e) {
			e.printStackTrace();				
		} catch (IOException e) {
			e.printStackTrace();			
		}		

		client.getConnectionManager().shutdown();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if (callback != null) {
			callback.onResponseSendPhoto();
		}	
	}
}
