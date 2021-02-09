package com.common.mvp;

public interface IRoute<P, R> {
	R call(P p);
}
