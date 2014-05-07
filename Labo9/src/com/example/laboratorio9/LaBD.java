package com.example.laboratorio9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LaBD extends SQLiteOpenHelper{

	private static LaBD miLaBD;
	private SQLiteDatabase db = getWritableDatabase();

	private LaBD(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public static LaBD getMiBD(Context context) {
		if (miLaBD == null) {
			miLaBD = new LaBD(context, "aDataBase", null, 1);
		}
		return miLaBD;
	}

	//CREAMOS la base de datos; las claves primarias seran; el nombre de la casa y de la subasta respectivamente
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE 'usuarios' ('tfno_usuario' INT PRIMARY KEY)");
		db.execSQL("CREATE TABLE 'favoritos' ('cod_favoritos' INT PRIMARY KEY AUTO_INCREMENT ,'usuario' TEXT,'cod_linea' INTEGER )");
		db.execSQL("CREATE TABLE 'llegadas' ('cod_llegada' INT PRIMARY KEY AUTO_INCREMENT, 'linea' TEXT, 'parada' TEXT, 'hora' DATE )");
		db.execSQL("CREATE TABLE 'lineas' ('cod_linea' INT PRIMARY KEY AUTO_INCREMENT, 'nombre_linea' TEXT, 'parada_inicio' TEXT, 'parada_fin' TEXT )");
		db.execSQL("CREATE TABLE 'paradas' ('cod_parada' INT PRIMARY KEY AUTO_INCREMENT, 'direccion' TEXT)");
		
		
		//INSERCION DE CASAS
		ContentValues values = new ContentValues();
		values.put("nombre", "subastas Bilbao XXI");
		db.insert("casa", "nombre", values);
		
		ContentValues values1 = new ContentValues();
		values1.put("nombre", "subastas Adjudicado");
		db.insert("casa", "nombre", values1);
		
		ContentValues values2 = new ContentValues();
		values2.put("nombre", "puja por Arte");
		db.insert("casa", "nombre", values2);
		
		ContentValues values3 = new ContentValues();
		values3.put("nombre", "todo subastas");
		db.insert("casa", "nombre", values3);
		
		ContentValues values4 = new ContentValues();
		values4.put("nombre", "spujas Gran Vía");
		db.insert("casa", "nombre", values4);
		
		
		ContentValues values5 = new ContentValues();
		values5.put("nombre", "todos pujan");
		db.insert("casa", "nombre", values5);

		//INSERCION DE FAVORITAS
		ContentValues values6 = new ContentValues();
		values6.put("nombre", "subastas Bilbao XXI_FAV");
		values6.put("valoracion", 0);
		db.insert("favoritas", "nombre", values6);
		
		ContentValues values7 = new ContentValues();
		values7.put("nombre", "subastas Adjudicado_FAV");
		values7.put("valoracion", 0);
		db.insert("favoritas", "nombre", values7);
		
		ContentValues values8 = new ContentValues();
		values8.put("nombre", "puja por Arte_FAV");
		values8.put("valoracion", 0);
		db.insert("favoritas", "nombre", values8);
	}

	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE casa");
		db.execSQL("DROP TABLE subasta");
	}
	
	//INSERTAMOS
	public void insertarCasa(String pClave) {
		ContentValues values = new ContentValues();
		values.put("nombre", pClave);
		
		db.insert("casa", "nombre", values);
	}
	
	public void insertarFavorita(String pClave) {
		ContentValues values = new ContentValues();
		values.put("nombre", pClave);
		values.put("valoracion", 0);
		db.insert("favoritas", "nombre", values);
	}
	
	public void insertarSubasta(String pNombreCasa, String pNombreSubasta, int pUltimaPuja) {
		String sql = "INSERT INTO subasta ('nombre_subasta','nombre_casa','ultima_puja') VALUES ('" + pNombreSubasta+ "','"
	+ pNombreCasa+ "','" + pUltimaPuja+ "')";
		this.db.execSQL(sql);
	}
	//SELECCIONAMOS
	public Cursor seleccionarCasas() {
		return db.query("casa", 
				new String[] {"nombre"}, null, null, null, null, null);
	}
	
	public Cursor seleccionarFavoritas() {
		return db.query("favoritas", 
				new String[] {"nombre","valoracion"}, null, null, null, null, null);
	}
	public Cursor seleccionarValoraciones(String pNombre) {
		String sql = "SELECT valoracion FROM favoritas WHERE nombre ='" + pNombre + "'";
		return db.rawQuery(sql, null);
	}

	public Cursor seleccionarSubastas() {
		String sql = "select * from subasta";
		return db.rawQuery(sql, null);
	}
	
	//MODIFICAMOS
	public void añadirValoracion(String pNom) {
		String sql = "UPDATE favoritas SET valoracion=1 WHERE nombre ='" + pNom + "'";
		this.db.execSQL(sql);
	}
	//ELIMINAMOS
	public void eliminarCasa(String pNombre) {
		String sql = "DELETE FROM casa WHERE nombre ='" + pNombre + "'";
		this.db.execSQL(sql);
	}
	
	public void eliminarFavorita(String pNombre) {
		String sql = "DELETE FROM favoritas WHERE nombre ='" + pNombre + "'";
		this.db.execSQL(sql);
	}
	
	public void eliminarSubasta(int pId) {
		String sql = "DELETE FROM subasta WHERE nombre ='" + pId + "'";
		this.db.execSQL(sql);
	}
	
	public void borrarTablas() {
		String sql = "drop table casa; drop table subasta";
		db.execSQL(sql);
	}
}