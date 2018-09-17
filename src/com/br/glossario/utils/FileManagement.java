package com.br.glossario.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class FileManagement {

	private Context context;

	public FileManagement(Context context) {
		this.context = context;
	}

	
	public boolean checkSdPresent(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED);
	}
	
	
	public File openOrCreateDirectory(String nameDir) {
		File dataDir = openDir(nameDir);// open directory for work
		dataDir.mkdirs();
		return dataDir;
	}
	
	
	public File openOrCreateDirectoryExternal(String pathDir){
		
		File file = new File(Environment.getExternalStorageDirectory(), pathDir);
		file.mkdirs();
		
		return file;
	}

	
	public void deleteDirectory(String nameDir) {
		File dataDir = openDir(nameDir);// open directory for work
		dataDir.delete();
	}

	
	public void CriaDiretorio(String dataDir, String idEstabelecimento,
			String[] dirEstabelecimento) {

		File sub = this.createSubDirectory(dataDir, idEstabelecimento);

		for (String s : dirEstabelecimento) {
			File myFile = new File(sub.getAbsolutePath(), s);
			myFile.mkdirs();
		}
	}

	
	public boolean ifExistDiretorio(File dirRoot, String dirBuscado) {

		File filDir = new File(dirRoot.getAbsolutePath(), dirBuscado);

		if (filDir.exists()) {
			return true;
		} else {
			return false;
		}
	}

	
	public File returnDirEspecifico(File dirRoot, String codigoEstabelecimento, String dirBuscado) {

		File filDir = new File(dirRoot.getAbsolutePath(), codigoEstabelecimento);

		if (filDir.exists()) {
			File fsub = new File(filDir.getAbsolutePath(), dirBuscado);

			if (fsub.exists()) {
				return fsub;
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	
	public File createSubDirectory(String nameDir, String nameSubDir) {

		File dataDir = openDir(nameDir);
		File myFile = new File(dataDir.getAbsolutePath(), nameSubDir);
		myFile.mkdirs();
		return myFile;
	}

	
	private File openDir(String nmDir) {
		File dataDir = context.getDir(nmDir, Context.MODE_PRIVATE);
		return dataDir;
	}

	/*---------------------------------------------------------------------------------------------------------------*/
	/*
	 public String [] ReadFileCut(File dataDir, String nmFile){
		  
		  File myFile = new File(dataDir.getPath(), nmFile); 
		  FileInputStream in;
		  String [] arrayTbDb = null; 
		  int indice = 0;
		 
		  try { 
			  	in = new FileInputStream(myFile); 
			  	InputStreamReader isr = new  InputStreamReader(in);
			  	
			  	long TAM = myFile.length(); 
			  	char[] inBuffer = new char[(int)TAM]; 
			  	int chaReady;
		  
				 while((chaReady = isr.read(inBuffer)) > 0){
					 arrayTbDb[indice] = String.copyValueOf(inBuffer, 0, chaReady); 
					 inBuffer = new char[(int)TAM];
				     indice++; 
				  } in.close();
				 
				  return arrayTbDb;
				  
				  } catch (FileNotFoundException e) { 
					  
				  } catch (IOException e) { 
					  
				  } return
				  null; 
	}*/
	
	
	public boolean saveBitmapInInternalStorage(File dir, Bitmap bitmap) {

		FileOutputStream fo = null;

		try {
			
			fo = new FileOutputStream(dir);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public List<String> LerTxt(String nmDir, String nmFile) {

		File myFile = new File(nmDir, nmFile);
		FileInputStream in;

		List<String> Txt = new ArrayList<String>();

		try {
			in = new FileInputStream(myFile);

			BufferedReader rd = new BufferedReader(new InputStreamReader(in,"UTF-8"), 4096);
			String line;

			try {
				while ((line = rd.readLine()) != null) {
					Txt.add(line);
				}

				rd.close();
				return Txt;

			} catch (IOException e) {
				return null;
			}

		} catch (Exception e) {
			return null;
		}

	}

	
	public String iStream_to_String(InputStream is1) {

		BufferedReader rd = new BufferedReader(new InputStreamReader(is1), 4096);
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
		String contentOfMyInputStream = sb.toString();
		return contentOfMyInputStream;
	}

	
	public void saveFileInDirectory(String nmDir, String nmFile) {

		File dataDir = openDir(nmDir);// open directory for work
		File myFile = new File(dataDir.getPath(), nmFile);

		try {

			FileOutputStream out = new FileOutputStream(myFile);
			String hello = "Este arquivo guarda o log do usuario !";
			out.write(hello.getBytes());
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void copy(File origem,File destino,boolean overwrite) throws IOException{ 
        
        if (destino.exists() && !overwrite){ 
             System.err.println(destino.getName()+" já existe, ignorando..."); 
             return; 
        } 
   
        FileInputStream   fisOrigem = new FileInputStream(origem); 
        FileOutputStream fisDestino = new FileOutputStream(destino); 
        FileChannel fcOrigem = fisOrigem.getChannel();   
        FileChannel fcDestino = fisDestino.getChannel();   
        fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);   
        fisOrigem.close();   
        fisDestino.close(); 
    }
  
	
    public void copyAll(File origem,File destino,boolean overwrite) throws IOException{ 
        
        if (!destino.exists()) 
             destino.mkdir(); 
       
          if (!origem.isDirectory()) 
             
             throw new UnsupportedOperationException("Origem deve ser um diretório"); 
          if (!destino.isDirectory())
             
            throw new UnsupportedOperationException("Destino deve ser um diretório"); 
              File[] files = origem.listFiles(); 
         
              for (File file : files) { 
            
                  if (file.isDirectory()) 
                      copyAll(file,new File(destino + File.separator + file.getName()),overwrite); 
                 
                  else{ 
   
                      System.out.println("Copiando arquivo: "+file.getName()); 
                      copy(file, new File(destino + File.separator +file.getName()),overwrite); 
                  }     
          } 
    } 
	
    
	public void FileTransferencia(String pathOrigem, File patchDestino) {
		
		File fo = new File(pathOrigem);		
		File fd = new File(patchDestino.getPath() ,fo.getName());		
		
		try{
			
			 FileInputStream fileInpu = new FileInputStream(fo);			
			 FileOutputStream fout = new FileOutputStream(fd);
			
			 int bytesLidos = 0;
			 byte[] buffer = new byte[1024];

			 while ((bytesLidos = fileInpu.read(buffer)) > 0) {
				 fout.write(buffer, 0, bytesLidos);
			 }
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void deleteFileInDirectory(String nameDir, String nameFile) {

		File dataDir = openDir(nameDir); // open directory for work
		File[] f = dataDir.listFiles();

		for (int i = 0; i <= f.length; i++) {

			String n = f[i].getName();

			if (n == nameFile) {
				f[i].deleteOnExit();
				Log.d("FILES", n);
			} else {
				Log.d("FILES", "NAO DELETADO");
			}
		}
	}

	
	public void saveImageToInternalStorage(String nmDir, String nmFile, Bitmap image) {

		FileOutputStream fo = null;
		try {
			File f = new File(nmDir, nmFile);
			fo = new FileOutputStream(f);
			image.compress(Bitmap.CompressFormat.PNG, 100, fo);
			fo.close();

		} catch (Exception e) {
		} finally {

			if (fo != null) {

				try {
					fo.close();
				} catch (IOException e) {
					Log.d("FAIL IN CLOSING ( FileOutputStream )", "[ fo ]");
				}
			}
		}
	}

	
	public Bitmap[] AllImagesFromInternalStorage(File nmDir) {

		FileInputStream fi = null;

		File f = new File(nmDir.getPath());

		File[] fotos = f.listFiles();

		Bitmap[] thumbnail = new Bitmap[fotos.length];

		for (int i = 0; i < fotos.length; i++) {
			try {
				fi = new FileInputStream(fotos[i]);
				thumbnail[i] = BitmapFactory.decodeStream(fi);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return thumbnail;
	}

	
	public Bitmap retornaImageFromInternalStorage(File img) {

		Bitmap thumbnail = null;
		FileInputStream fi = null;

		try {
			fi = new FileInputStream(img);
			thumbnail = BitmapFactory.decodeStream(fi);
			fi.close();
			return thumbnail;

		} catch (Exception ex) {
		} finally {

			if (fi != null) {

				try {
					fi.close();
				} catch (IOException e) {
					Log.d("FAIL IN CLOSING ( FileInputStream )", "[ in ]");
				}
			}
		}
		return null;
	}

	
	public void unzip(File zipFile, File dir) throws IOException {

		ZipFile zip = null;
		File arquivo = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[1024];

		try {

			if (!dir.exists()) {
				dir.mkdirs();
			}

			if (!dir.exists() || !dir.isDirectory()) {
				throw new IOException("O diretório " + dir.getName()+ " não é um diretório válido");
			}

			zip = new ZipFile(zipFile);

			Enumeration<?> e = zip.entries();

			while (e.hasMoreElements()) {
				ZipEntry entrada = (ZipEntry) e.nextElement();
				arquivo = new File(dir, entrada.getName());

				if (entrada.isDirectory() && !arquivo.exists()) {
					arquivo.mkdirs();
					continue;
				}

				if (!arquivo.getParentFile().exists()) {
					arquivo.getParentFile().mkdirs();
				}
				try {

					is = zip.getInputStream(entrada);
					os = new FileOutputStream(arquivo);
					int bytesLidos = 0;

					if (is == null) {
						throw new ZipException("Erro ao ler a entrada do zip: "
								+ entrada.getName());
					}

					while ((bytesLidos = is.read(buffer)) > 0) {
						os.write(buffer, 0, bytesLidos);
					}

				} finally {

					if (is != null) {
						try {
							is.close();
						} catch (Exception ex) {
						}
					}

					if (os != null) {
						try {
							os.close();
						} catch (Exception ex) {
						}
					}
				}
			}

		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (Exception e) {
				}
			}
		}
	}

	
	public void CompressDiretorio(File DirRoot, String _zipFile){
		
		int BUFFER = 2048;
		
		try {
			 
		      BufferedInputStream origin = null; 
		      FileOutputStream dest = new FileOutputStream(_zipFile); 
		 
		      ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest)); 
		 
		      byte data[] = new byte[BUFFER]; 
		      		      
		      String []_files = DirRoot.list();
		 
		      for(int i = 0; i < _files.length; i++) { 
		        
		    	  	Log.v("Compress", "Adding: " + _files[i]); 
			        FileInputStream fi = new FileInputStream(_files[i]); 
			        
			        origin = new BufferedInputStream(fi, BUFFER); 
			        
			        ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1)); 
			        out.putNextEntry(entry); 
			        
			        int count; 
			       
			        while ((count = origin.read(data, 0, BUFFER)) != -1) { 
			          out.write(data, 0, count); 
			        }
		        
		        origin.close(); 
		      } 
		 
		      out.close(); 
		      
		    } catch(Exception e) { 
		      e.printStackTrace(); 
		    } 		 
	}
	
	
	public File listALL(File DirRoot) {

		if (DirRoot.isDirectory()) {
			
			System.out.println("length : " + DirRoot.listFiles().length);
			
			for (File f : DirRoot.listFiles()) {

				if (f.isDirectory()) {
					System.out.println("rexu . Patch : " + f.getPath());
					return apagaDiretorioRecursivamente(f);						
				} else {

					System.out.println("Patch : " + f.getPath());
				}
			}
		}
		return DirRoot;
	}
	
	
	public File apagaDiretorioRecursivamente(File DirRoot) {

		if (DirRoot.isDirectory()) {
			for (File f : DirRoot.listFiles()) {

				if (f.isDirectory()) {
					apagaDiretorioRecursivamente(f).delete();
				} else {
					f.delete();
				}
			}
		}
		return DirRoot;
	}
	
	
	public File apagaDiretorio(File DirRoot, File DirChild) {

		if (DirRoot.isDirectory()) {
			for (File f : DirRoot.listFiles()) {

				if (f.getName().equals(DirChild.getName())) {
					DirChild.delete();
				} 
			}
		}
		return DirRoot;
	}
	
	public Uri fileImage(String pathDir){
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "/IMG_" + timeStamp + ".png";
		
		File pathFoto = new File(pathDir, imageFileName);
		
		Uri uriImage = Uri.fromFile(pathFoto);
		
		return uriImage; 
	}
	
}
