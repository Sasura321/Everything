package com.github.v1.core.interceptor;

import com.github.v1.core.model.Thing;

/**
 * 检索结果Thing的拦截器
 */
public interface ThingInterceptor {

    void apply(Thing thing);
}
