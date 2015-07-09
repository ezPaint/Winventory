package com.simoncomputing.app.winventory.domain;

/**
 * Anything with a barcode should implement this (ie, Hardware, User, etc)
 * Also allows support for associations of barcodes by just returning List<Barcoded>
 * @author nathaniel.lahn
 *
 */
public interface Barcoded {
	public String getBarcode();
}
