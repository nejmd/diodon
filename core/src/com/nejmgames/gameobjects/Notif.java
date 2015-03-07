package com.nejmgames.gameobjects;

public class Notif {
	int x,y;
	
	float alpha;
	String msg;
	
	
	
	public Notif(int x, int y, float alpha, String msg) {
		this.x = x;
		this.y = y;
		this.alpha = alpha;
		this.msg = msg;
	}
	
	public void set(int x, int y, float alpha, String msg) {
		this.x = x;
		this.y = y;
		this.alpha = alpha;
		this.msg = msg;
	}
	
	public void onRestart(){
		this.alpha = 0;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
