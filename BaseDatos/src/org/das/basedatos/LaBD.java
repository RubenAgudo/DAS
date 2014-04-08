package org.das.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LaBD extends SQLiteOpenHelper{

	private static LaBD miLaBD;
	private SQLiteDatabase db = getWritableDatabase();
	
	private LaBD(Context context, String name, CursorFactory factory, int version)  {
		super(context, name, factory, version);
	}
	
	public static LaBD getMiBD(Context context) {
		if(miLaBD == null) {
			miLaBD = new LaBD(context, "aDataBase", null, 1);
		 
		}
		
		return miLaBD;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Usuarios ('Codigo' INTEGER PRIMARY KEY	AUTOINCREMENT NOT NULL, 'Nombre' TEXT)");
		db.execSQL("CREATE TABLE Coche ('Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Nombre' TEXT)");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertar(String pClave){
		String sql = "INSERT INTO Usuarios ('Nombre') VALUES ('"+ pClave +"')";
		this.db.execSQL(sql);
	}
	
	public Cursor seleccionar(){
		String sql = "select * from Usuarios";
		return db.rawQuery(sql, null);
	}

	public void eliminar(String pNombre) {
		String sql = "DELETE FROM Usuarios WHERE Nombre ='" + pNombre + "'";
		this.db.execSQL(sql);
	}

	public void eliminar(int position) {
		String sql = "DELETE FROM Usuarios WHERE Codigo ='" + position + "'";
		this.db.execSQL(sql);
	}
}
