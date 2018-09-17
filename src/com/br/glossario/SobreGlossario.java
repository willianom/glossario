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
		text+= "Aplicativo � destinado a fins educacionais, para atividades colaborativas com base na t�cnica de aprendizagem "
				+ "colaborativa Group Grid (mais informa��es consultar o livro: Collaborative Learning Techniques - Barkley, "
				+ "Cross and Major). A aplica��o foi modelada para surdos, em atividades da disciplina de l�ngua portuguesa. "
				+ "Por�m, o aplicativo pode ser facilmente utilizada em outras disciplinas curriculares, desde que as atividades "
				+ "estejam elaboradas seguindo as diretrizes da t�cnica Group Grid, mencionada anteriormente. Para disponibiliza��o "
				+ "e autoriza��o de uso acad�mico do aplicativo, � ESTRITAMENTE necess�rio entrar em contato com o autor do projeto "
				+ "atrav�s do email: eltonranieremoura@gmail.com.";
		text+= "</p></body></html>";
		
		view.loadData(text, "text/html", "utf-8");
	}
}
