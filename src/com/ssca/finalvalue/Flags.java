package com.ssca.finalvalue;

public final class Flags {
	public static final int MH_NOUNDEFS = 0x1;
	public static final int MH_INCRLINK = 0x2;
	public static final int MH_DYLDLINK = 0x4;
	public static final int MH_BINDATLOAD = 0x8;
	public static final int MH_PREBOUND = 0x10;
	public static final int MH_SPLIT_SEGS = 0x20;
	public static final int MH_LAZY_INIT = 0x40;
	public static final int MH_TWOLEVEL = 0x80;
	public static final int MH_FORCE_FLAT = 0x100;
	public static final int MH_NOMULTIDEFS = 0x200;
	public static final int MH_NOFIXPREBINDING = 0x400;
	public static final int MH_PREBINDABLE = 0x800;
	public static final int MH_ALLMODSBOUND = 0x1000;
	public static final int MH_SUBSECTIONS_VIA_SYMBOLS = 0x2000;
	public static final int MH_CANONICAL = 0x4000;
	public static final int MH_WEAK_DEFINES = 0x8000;
	public static final int MH_BINDS_TO_WEAK = 0x10000;
	public static final int MH_ALLOW_STACK_EXECUTION = 0x20000;
	public static final int MH_ROOT_SAFE = 0x40000;
	public static final int MH_SETUID_SAFE = 0x80000;
	public static final int MH_NO_REEXPORTED_DYLIBS = 0x100000;
	public static final int MH_PIE = 0x200000;
	public static final int MH_DEAD_STRIPPABLE_DYLIB = 0x400000;
	public static final int MH_HAS_TLV_DESCRIPTORS = 0x800000;
	public static final int MH_NO_HEAP_EXECUTION = 0x1000000;
}
