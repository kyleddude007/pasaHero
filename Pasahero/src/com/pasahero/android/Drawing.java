package com.pasahero.android;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;

public class Drawing {

	private Paint paint;
	private List<GeoPoint> lineData;
	private LineOverlay lineOverlay;
	private MapView map;
	private List<Overlay> overlays;
	private int color;
	

	public Drawing(MapView map, List<GeoPoint> lineData, List<Overlay> overlays, int color){
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.lineData = lineData;
		this.lineOverlay = new LineOverlay(paint);
		this.map = map;
		this.overlays = overlays;
		this.color = color;
		setStyle();
		
	}

	
	public Drawing(MapView map, List<GeoPoint> lineData, List<Overlay> overlays){
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		setStyle();
		this.lineData = lineData;
		this.lineOverlay = new LineOverlay(paint);
		this.map = map;
		this.overlays = overlays;
	}

	
	
	public Drawing(MapView map, List<GeoPoint> lineData, LineOverlay lineOverlay){
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.lineData = lineData;
		this.lineOverlay = lineOverlay;
		this.map = map;
		setStyle();
	}
	
	public Drawing(MapView map){
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.map = map;
		this.lineOverlay = new LineOverlay();
		setStyle();
	}
	
	private void setStyle(){
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(Config.ROUTE_STROKE_WIDTH);
	}
	
	public void draw(){
		lineOverlay.setData(lineData);
		overlays.add(lineOverlay);
		map.invalidate();
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public List<GeoPoint> getLineData() {
		return lineData;
	}

	public void setLineData(List<GeoPoint> lineData) {
		this.lineData = lineData;
	}

	public LineOverlay getLineOverlay() {
		return lineOverlay;
	}

	public void setLineOverlay(LineOverlay lineOverlay) {
		this.lineOverlay = lineOverlay;
	}

	public MapView getMap() {
		return map;
	}

	public void setMap(MapView map) {
		this.map = map;
	}

	
}
