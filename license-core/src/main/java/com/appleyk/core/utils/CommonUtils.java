package com.appleyk.core.utils;

import java.util.*;

/**
 * <p>常用判断和集合转换工具类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class CommonUtils {

    public CommonUtils() {
    }

    public static String validStringValue(Object object) {
        return object == null ? "" : object.toString();
    }

    public static int validIntValue(Object object) {
        return object == null ? 0 : Integer.valueOf(object.toString());
    }

    public static long validLongValue(Object object) {
        return object == null ? 0L : Long.valueOf(object.toString());
    }

    public static boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) > 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) > 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() > 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() > 0;
        } else if (object instanceof Boolean) {
            return Boolean.valueOf(object.toString());
        } else if (object instanceof List) {
            return ((List)object).size() > 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() > 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() > 0;
        } else if (object instanceof Iterator) {
            return ((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() > 0;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) == 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) == 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() == 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() == 0;
        } else if (object instanceof Boolean) {
            return Boolean.valueOf(object.toString());
        } else if (object instanceof List) {
            return ((List)object).size() == 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() == 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() == 0;
        } else {
            return false;
        }
    }

    public static List<Long> set2List(Set<Long> sets) {
        List<Long> list = new ArrayList();
        if (sets != null && sets.size() > 0) {
            Iterator var2 = sets.iterator();

            while(var2.hasNext()) {
                Object o = var2.next();
                Long id = Long.valueOf(o.toString());
                list.add(id);
            }

            return list;
        } else {
            return null;
        }
    }

    public static List<String> set2ListStr(Set<?> set) {
        ArrayList result = new ArrayList();

        try {
            if (set.size() > 0) {
                set.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("HashSet转换失败");
        }

        return result;
    }

    public static Set<Long> list2Set(List<String> list) {
        HashSet result = new HashSet();

        try {
            if (list.size() > 0) {
                list.forEach((s) -> {
                    result.add(Long.parseLong(s));
                });
            }
        } catch (Exception var3) {
            System.err.println("List转换失败");
        }

        return result;
    }

    public static Set<Long> hashSetToSet(HashSet<?> sets) {
        Set<Long> result = new HashSet();
        if (sets != null && sets.size() > 0) {
            Iterator var2 = sets.iterator();

            while(var2.hasNext()) {
                Object o = var2.next();
                Long id = Long.valueOf(o.toString());
                result.add(id);
            }

            return result;
        } else {
            return null;
        }
    }

    public static List<String> setToList(Set<?> set) {
        ArrayList result = new ArrayList();
        try {
            if (set.size() > 0) {
                set.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("HashSet转换失败");
        }
        return result;
    }

    public static List<String> listToList(List<?> list) {
        ArrayList result = new ArrayList();
        try {
            if (list.size() > 0) {
                list.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("List转换失败");
        }
        return result;
    }

    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        } else {
            String result = "";

            try {
                if (list.size() <= 0) {
                    return result;
                }

                String s;
                for(Iterator var2 = list.iterator(); var2.hasNext(); result = result + s + ";") {
                    s = (String)var2.next();
                }

                result = result.substring(0, result.length() - 1);
            } catch (Exception var4) {
                System.err.println("List转换失败");
            }

            return result;
        }
    }

    public static List<String> StringTolist(String str) {
        if (str != null && str.length() != 0) {
            ArrayList list = new ArrayList();

            try {
                String[] strs = str.substring(1, str.length() - 1).replaceAll("\"", "").split(",");
                String[] var3 = strs;
                int var4 = strs.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String result = var3[var5];
                    list.add(result);
                }
            } catch (Exception var7) {
                System.err.println("List转换失败");
            }

            return list;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        Integer a = null;
        System.out.println(isNotEmpty(a));
        String str = "[\"11\",\"222\"]";
        List<String> list = StringTolist(str);
        System.out.println();
    }
}
