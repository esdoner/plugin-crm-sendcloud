package com.fr.plugin.sendcloud.model;

import com.fr.plugin.sendcloud.exception.ContentException;

public interface Content {
	public boolean useTemplate();

	public boolean validate() throws ContentException;
}