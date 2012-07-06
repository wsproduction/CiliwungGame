package com.wsp.ciliwunggame;

public class Contact {
	int _id;
	String _name;
	String _phone_number;

	public Contact() {
		// TODO Auto-generated constructor stub
	}
	
	public Contact(int id) {
		this._id = id;
	}
	
	public Contact(String name, String phone_number) {
		this._name = name;
		this._phone_number = phone_number;
	}

	public Contact(int id, String name, String phone_number) {
		this._id = id;
		this._name = name;
		this._phone_number = phone_number;
	}

	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getPhoneNumber() {
		return _phone_number;
	}

	public void setPhoneNumber(String _phone_number) {
		this._phone_number = _phone_number;
	}

}
