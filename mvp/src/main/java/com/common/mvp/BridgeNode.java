package com.common.mvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BridgeNode {

    private BridgeNode mParent;
    private Set<BridgeNode> mChildren = new HashSet<>();

    private Map<Class<IRoute>, IRoute> mRouteMap = new HashMap<>();

    public BridgeNode(BridgeNode parent) {
        mParent = parent;
    }

    public void addChild(BridgeNode child) {
        if (mChildren != null) {
            mChildren.add(child);
        }
    }

    public void release() {
        mParent = null;

        if (mChildren != null) {
            mChildren.clear();
            mChildren = null;
        }

        if (mRouteMap != null) {
            mRouteMap.clear();
            mRouteMap = null;
        }

    }

    public void newRoute(IRoute<?, ?> route) {
        if (route == null) {
            return;
        }
        Class<IRoute> iClass = getAssignableClassFrom(route.getClass());
        if (iClass != null && mRouteMap != null) {
            mRouteMap.put(iClass, route);
        }
    }

    public <T extends IRoute> T getRoute(Class<T> tClass) {
        BridgeNode root = getRootBridgeNode();
        return root.getRouteFromChild(tClass);
    }


    private <T extends IRoute> T getRouteFromChild(Class<T> tClass) {
        T route = null;
        if (mRouteMap != null) {
            route = (T) mRouteMap.get(tClass);
        }

        if (route == null && mChildren != null) {
            for (BridgeNode child : mChildren) {
                route = child.getRouteFromChild(tClass);
                if (route != null) {
                    break;
                }
            }
        }
        return route;
    }

    public <T extends IRoute> List<T> getAllSuchRoutes(Class<T> tClass) {
        BridgeNode root = getRootBridgeNode();
        return root.getAllSuchRoutesFromChild(tClass);
    }

    private BridgeNode getRootBridgeNode() {
        if (mParent == null) {
            return this;
        }
        return mParent.getRootBridgeNode();
    }

    private <T extends IRoute> List<T> getAllSuchRoutesFromChild(Class<T> tClass) {
        List<T> routes = new ArrayList<>();
        if (mRouteMap != null) {
            T route = (T) mRouteMap.get(tClass);
            if (route != null) {
                routes.add(route);
            }
        }

        if (mChildren != null) {
            for (BridgeNode child : mChildren) {
                List<T> routesFromChild = child.getAllSuchRoutesFromChild(tClass);
                routes.addAll(routesFromChild);
            }
        }
        return routes;
    }

    private Class<IRoute> getAssignableClassFrom(Class aClass) {
        if (aClass.isInterface()) {
            if (IRoute.class.isAssignableFrom(aClass)) {
                return aClass;
            }
        }

        for (Class<?> iClass : aClass.getInterfaces()) {
            Class iiClass = getAssignableClassFrom(iClass);
            if (iiClass != null) {
                return iiClass;
            }
        }

        if (aClass.getSuperclass() != null) {
            return getAssignableClassFrom(aClass.getSuperclass());
        }

        return null;
    }
}
