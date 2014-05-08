package org.das.ninjamessaging.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class LaBD extends SQLiteOpenHelper{

	private static LaBD miLaBD;
	private long seq;
	private SQLiteDatabase db= getWritableDatabase();
	private static final String[] DOGE_MESSAGES = {"Wow", "So message", "Such important", "Many notifications", "Y U do dis?"};
	
	private LaBD(Context context, String name, CursorFactory factory, int version)  {
		super(context, name, factory, version);
		Cursor aCursor = db.rawQuery("Select Max(SEQ) from Mensajes", null);
		if(aCursor.moveToFirst()) {
			seq = aCursor.getLong(0);
		}
	}
	
	public static LaBD getMiBD(Context context) {
		if(miLaBD == null) {
			miLaBD = new LaBD(context, "NinjaMessenger", null, 2);
		}
		
		return miLaBD;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		seq = 0;
		//creamos la tabla de mensajes
		db.execSQL("CREATE TABLE 'Mensajes' "
					+ "('NombreUsuario' TEXT NOT NULL , "
					+ "'SEQ' INTEGER NOT NULL, "
					+ "'Texto' TEXT, "
					+ "'Fecha' DATETIME DEFAULT CURRENT_DATE, "
					+ "'EnviadoPorMi' INTEGER NOT NULL, "
					+ "PRIMARY KEY ('NombreUsuario', 'SEQ'))");
		
		//Creamos la tabla de usuarios
		db.execSQL("CREATE TABLE 'Usuarios' "
					+ "('NombreUsuario' TEXT PRIMARY KEY  NOT NULL  UNIQUE , "
					+ "'NombreReal' TEXT, "
					+ "'Apellido' TEXT, "
					+ "'Telefono' TEXT)");
		
		//Creamos la tabla de los chats recientes.
		db.execSQL("CREATE TABLE 'ChatsRecientes' ('Usuario' TEXT PRIMARY KEY  NOT NULL )");
		
		db.execSQL("insert into 'Usuarios' (NombreUsuario) values ('Foo')" );
		db.execSQL("insert into 'Usuarios' (NombreUsuario) values ('Bar')" );
		
		db.execSQL("insert into 'ChatsRecientes' (Usuario) values ('Foo')" );
		db.execSQL("insert into 'ChatsRecientes' (Usuario) values ('Bar')" );
		
		db.execSQL("insert into 'Mensajes' (NombreUsuario, SEQ, Texto, EnviadoPorMi) values ('Foo'," + seq++ + ", 'Hola', 0)" );
		db.execSQL("insert into 'Mensajes' (NombreUsuario, SEQ, Texto, EnviadoPorMi) values ('Foo'," + seq++ + ", 'Que tal', 0)" );
		db.execSQL("insert into 'Mensajes' (NombreUsuario, SEQ, Texto, EnviadoPorMi) values ('Foo'," + seq++ + ", 'Bien', 1)" );
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE Mensajes");
		db.execSQL("DROP TABLE Usuarios");
		db.execSQL("DROP TABLE ChatsRecientes");
		
	}
	
	/**
	 * Metodo que devuelve todos los mensajes que tienes con un usuario
	 * @param user
	 * @return
	 */
	public Cursor getMessagesWithUser(String user) {
		
		return db.query("Mensajes", //tabla
				new String[] {"NombreUsuario", "Texto","EnviadoPorMi"},  //columnas
				"NombreUsuario=?"  , //where nombreusuario
				new String[] {user}, //= user
				null, //groupby
				null, //having
				"SEQ desc"); //orderby
	}
	

	/**
	 * Metodo que devuelve los offset mensajes que tienes con un usuario
	 * @param user el usuario
	 * @param offset Cuantos mensajes quieres
	 * @return
	 */
	public Cursor getMessagesWithUser(String user, int offset) {
		
		return db.query("Mensajes", //tabla
				new String[] {"NombreUsuario", "Texto", "EnviadoPorMi"},  //columnas
				"NombreUsuario=?"  , //where nombreusuario
				new String[] {user}, //= user
				null, //groupby
				null, //having
				"SEQ desc LIMIT " + offset); //orderby
	}

	/**
	 * Obtiene tus chats recientes
	 * @return
	 */
	public Cursor getRecentChats() {
		return db.query("ChatsRecientes", //tabla
				new String[] {"Usuario"},  //columnas
				null  , //where nombreusuario
				null, //= user
				null, //groupby
				null, //having
				null); //orderby
	}

	/**
	 * A�ade un nuevo mensaje a la conversacion con cierto usuario
	 * @param hablandoCon El usuario con el que estas hablando
	 * @param message el mensaje
	 * @param enviadoPor 1 = enviado por el movil, 0 = enviado por tu contacto
	 */
	public void anadirMensaje(String hablandoCon, String message, int enviadoPor) {
		
		ContentValues values = new ContentValues();
		values.put("NombreUsuario", hablandoCon);
		values.put("Texto", message);
		values.put("EnviadoPorMi", enviadoPor);
		values.put("SEQ", seq++);
		db.insert("Mensajes", "NombreUsuario, Texto, EnviadoPorMi, SEQ", values);
		
		
		
	}

	/**
	 * Obtiene todos tus contactos para poder iniciar una conversacion
	 * @return un Cursor con todos tus usuarios ordenados alfabeticamente
	 */
	public Cursor getUsers() {
		return db.query("Usuarios", 
				new String[]{"NombreUsuario"}, 
				null, 
				null, 
				null, 
				null, 
				"NombreUsuario ASC");
	}
	
	/**
	 * Exporta la conversacion con cierto usuario a la SD
	 * @param user
	 * @return 
	 */
	public boolean exportarChat(String user) {
		
		File path = Environment.getExternalStorageDirectory();
		File f = new File(path.getAbsolutePath(), user + "  " + System.currentTimeMillis());
		OutputStreamWriter fich;
		try {
			fich = new OutputStreamWriter( new FileOutputStream(f));
			
			Cursor aCursor = getMessagesWithUser(user);
			
			if(aCursor.moveToFirst()) {
				do {
					String hablandoCon = aCursor.getString(0);
					String nombre = aCursor.getInt(2) == 1 ? "Yo" : hablandoCon;
					String mensaje = aCursor.getString(1);
					
					fich.write(nombre + ": " + mensaje + "\n");
					
				} while(aCursor.moveToNext());
			}
			fich.close();
			aCursor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return true;
		
	}

	/**
	 * Coje un usuario al azar, coge un mensaje al azar de DOGE_MESSAGES
	 * y lo inserta en la BD (mensaje recibido!) y devuelve el nombre del usuario
	 * y el texto para mostrarlo en la notificacion
	 * @return
	 */
	public String[] getRandomChat() {
		Cursor aCursor = getUsers();
		String nombre = null,
				mensaje = null;
		
		if(aCursor.moveToFirst()) {
			int rows = aCursor.getCount();
			
			Random rand = new Random();
			int resultado = rand.nextInt(rows);
			
			aCursor.moveToPosition(resultado);
			nombre = aCursor.getString(0);
			
			int mensajeSeleccionado = rand.nextInt(DOGE_MESSAGES.length);
			mensaje = DOGE_MESSAGES[mensajeSeleccionado];
			
			anadirMensaje(nombre, mensaje, 0);
		}
		aCursor.close();
		
		return new String[] {nombre, mensaje};
	}
	
	public Cursor getUserInfo(String user) {
		
		return db.query("Usuarios", null, "NombreUsuario=?", new String[]{user}, null, null, null);
		
	}

	public void actualizarInfoUsuario(String detallesDe,
			String nombreRealString, String apellidoString,
			String telefonoString) {
		ContentValues values = new ContentValues();
		values.put("NombreReal", nombreRealString);
		values.put("Apellido", apellidoString);
		values.put("Telefono", telefonoString);
		
		db.update("Usuarios", values, "nombreUsuario=?", new String[]{detallesDe});
		
	}

	public String[] getLastMessage(String user) {
		Cursor aCursor = db.query("Mensajes", 
				new String[]{"NombreUsuario, Texto"}, 
				"NombreUsuario=?", 
				new String[] {user}, 
				null, 
				null, 
				"SEQ DESC");
		if(aCursor.moveToFirst()) {
			
			return new String[] {aCursor.getString(0), aCursor.getString(1)};
		} else {
			return null;
		}
	}
	
	
}
