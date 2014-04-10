package org.das.ninjamessaging.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class LaBD extends SQLiteOpenHelper{

	private static LaBD miLaBD;
	private SQLiteDatabase db= getWritableDatabase();
	
	private LaBD(Context context, String name, CursorFactory factory, int version)  {
		super(context, name, factory, version);
	}
	
	public static LaBD getMiBD(Context context) {
		if(miLaBD == null) {
			miLaBD = new LaBD(context, "NinjaMessenger", null, 1);
			
		}
		
		return miLaBD;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//creamos la tabla de mensajes
		db.execSQL("CREATE TABLE 'Mensajes' "
					+ "('NombreUsuario' TEXT NOT NULL , "
					+ "'SEQ' INTEGER NOT NULL  DEFAULT CURRENT_TIMESTAMP, "
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
		
		db.execSQL("insert into 'Mensajes' (NombreUsuario, Texto, EnviadoPorMi) values ('Foo', 'Hola', 0)" );
		db.execSQL("insert into 'Mensajes' (NombreUsuario, Texto, EnviadoPorMi) values ('Foo', 'Que tal', 0)" );
		db.execSQL("insert into 'Mensajes' (NombreUsuario, Texto, EnviadoPorMi) values ('Foo', 'Bien', 1)" );
		
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
	 * Añade un nuevo mensaje a la conversacion con cierto usuario
	 * @param hablandoCon El usuario con el que estas hablando
	 * @param message el mensaje
	 * @param enviadoPor 1 = enviado por el movil, 0 = enviado por tu contacto
	 */
	public void anadirMensaje(String hablandoCon, String message, int enviadoPor) {
		
		ContentValues values = new ContentValues();
		values.put("NombreUsuario", hablandoCon);
		values.put("Texto", message);
		values.put("EnviadoPorMi", enviadoPor);
		db.insert("Mensajes", "NombreUsuario, Texto, EnviadoPorMi", values);
		
		
		
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
	 */
	public void exportarChat(String user) {
		
	}
	
	
}
