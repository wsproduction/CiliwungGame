package com.wsp.ciliwunggame;

public class Score {

	int _id;
	String _name;
	int _score;

	public Score() {

	}

	public Score(int id) {
		this._id = id;
	}
	
	public Score(String name, int score) {
		this._name = name;
		this._score = score;
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public int getScore() {
		return _score;
	}

	public void setScore(int score) {
		this._score = score;
	}

}
