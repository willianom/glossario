package com.br.glossario;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

public class SobreGlossario extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.sobre_glossario);
		
		WebView view = (WebView) findViewById(R.id.webview_desc);
		
		String text;
		text = "<html><body><p align=\"justify\">";
		text+= "Aplicativo é destinado a fins educacionais, para atividades colaborativas com base na técnica de aprendizagem "
				+ "colaborativa Group Grid (mais informações consultar o livro: Collaborative Learning Techniques - Barkley, "
				+ "Cross and Major). A aplicação foi modelada para surdos, em atividades da disciplina de língua portuguesa. "
				+ "Porém, o aplicativo pode ser facilmente utilizada em outras disciplinas curriculares, desde que as atividades "
				+ "estejam elaboradas seguindo as diretrizes da técnica Group Grid, mencionada anteriormente. Para disponibilização "
				+ "e autorização de uso acadêmico do aplicativo, é ESTRITAMENTE necessário entrar em contato com o autor do projeto "
				+ "através do email: eltonranieremoura@gmail.com.";
		text+= "</p></body></html>";
		
		view.loadData(text, "text/html", "utf-8");
	}
}
