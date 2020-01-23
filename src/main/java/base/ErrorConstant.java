package base;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
public class ErrorConstant {
    /**
     * 处理异常信息
     * @param e
     * @return
     */
    public static String dealExceptionMessage(Exception e) {
        if (e != null && e.getMessage() != null) {
            int errMessageEndIndex = 50;
            if (e.getMessage().length() >= errMessageEndIndex) {
                return e.getMessage().substring(0, errMessageEndIndex);
            } else {
                return e.getMessage();
            }
        }
        return "未知错误";
    }

    private static Map<String, Result> errorResultMap = new HashMap<>();

    public static final String TIME_OUT_MESSAGE = "服务超时";

    //操作成功！
    public static final String SUCCESS = "00000";

    //操作失败
    public static final String FAIL = "00001";

    //参数不能为空
    public static final String PARAM_NOT_NULL = "00002";

    //实体类中没有createInfo或createUser
    public static final String CREATE_USER_NOT_EXIST = "00003";

    //数据已产生变化，请刷新！
    public static final String DATA_CHANGED = "00004";

    //ID为空
    public static final String ID_IS_NULL = "00005";

    //参数校验不通过
    public static final String PARAM_IS_NULL = "00006";

    //已存在
    public static final String DATA_EXISTS = "00007";

    //存在激活或封存的数据，不允许删除
    public static final String NOT_DELETE = "00008";

    //只有保存状态的数据才能删除
    public static final String NO_DELETE = "07606";

    //分页参数错误
    public static final String PAGEINFO_ERROR = "00009";

    //未知错误
    public static final String UNKNOW_ERROR = "00010";

    //存在激活或封存的数据，不允许删除
    public static final String NOT_ALLOW_DELETE = "00011";

    //HSF服务调用失败
    public static final String HSF_SERVICE_ERROR = "00012";

    //导入excel,业务校验错误提示
    public static final String IMPORT_DATA_CODE = "00013";

    // 无符合条件的数据
    public static final String DATA_NOT_EXISTS = "00014";

    // 导入excel直接返回数据
    public static final String IMPORT_DATA_DATA = "00015";

    // 业务单据不存在
    public static final String ORDER_NOT_EXISTS = "00016";

    //特殊编码 柜台复制时，存在柜台，是否覆盖
    public static final String EXISTENCE_COVER = "33333";

    //特殊编码  购物汇总时 前端需要特殊编码写弹窗 要保证唯一
    public static final String SPECIAL_CODE = "22222";

    public static final String DISCOUNT_RIGHTS_CHECK = "00017";

    // 需要导出数据
    public static final String NEED_DOWNLOAD = "00018";
    // 需要确认
    public static final String NEED_CONFIRM = "00019";

    static {
        errorResultMap.put(SUCCESS, new Result(SUCCESS, "操作成功！", ""));
        errorResultMap.put(FAIL, new Result(FAIL, "操作失败！", ""));
        errorResultMap.put(PARAM_NOT_NULL, new Result(PARAM_NOT_NULL, "参数不能为空！", ""));
        errorResultMap.put(CREATE_USER_NOT_EXIST, new Result(CREATE_USER_NOT_EXIST, "实体类中没有createInfo或createUser！", ""));
        errorResultMap.put(DATA_CHANGED, new Result(DATA_CHANGED, "数据已产生变化，请刷新！", ""));
        errorResultMap.put(ID_IS_NULL, new Result(ID_IS_NULL, "ID为空！", ""));
        errorResultMap.put(PARAM_IS_NULL, new Result(PARAM_IS_NULL, "参数校验不通过！", ""));
        errorResultMap.put(PAGEINFO_ERROR, new Result(PAGEINFO_ERROR, "分页参数错误！", ""));
        errorResultMap.put(UNKNOW_ERROR, new Result(UNKNOW_ERROR, "未知错误！", ""));
        errorResultMap.put(NOT_DELETE, new Result(NOT_DELETE, "存在激活或封存的数据，不允许删除！", ""));
        errorResultMap.put(NO_DELETE, new Result(NO_DELETE, "只有保存状态的数据才能删除！", ""));
        errorResultMap.put(NOT_ALLOW_DELETE, new Result(NOT_ALLOW_DELETE, "单据非保存状态，不允许操作！", ""));
        errorResultMap.put(HSF_SERVICE_ERROR, new Result(HSF_SERVICE_ERROR, "HSF服务调用失败！", ""));
        errorResultMap.put(IMPORT_DATA_CODE, new Result(IMPORT_DATA_CODE, "导入失败！", ""));
        errorResultMap.put(DATA_NOT_EXISTS, new Result(DATA_NOT_EXISTS, "该记录不存在！", ""));
        errorResultMap.put(ORDER_NOT_EXISTS, new Result(ORDER_NOT_EXISTS, "该记录不存在！", ""));
        errorResultMap.put(DISCOUNT_RIGHTS_CHECK, new Result(DISCOUNT_RIGHTS_CHECK, "无对应权限,若已有权限请重新登录！", ""));
        errorResultMap.put(NEED_DOWNLOAD, new Result(NEED_DOWNLOAD, "部分数据未展示，是否下载？", ""));


    }

    /**
     * 抛出Error
     */
    public static Result getErrorResult(String errCode) {
        if (!SUCCESS.equals(errCode)) {
            return errorResultMap.get(errCode);
        }
        return null;
    }

    /**
     * 抛出Error
     */
    public static void getErrorException(String errCode) throws RuntimeException {
        if (!SUCCESS.equals(errCode)) {
            if (errorResultMap.containsKey(errCode)) {
                throw new RuntimeException(errorResultMap.get(errCode).getText());
            } else {
                throw new RuntimeException(errorResultMap.get(UNKNOW_ERROR).getText());
            }
        }
    }

    /**
     * 抛出Error
     */
    public static Result getErrorException(Exception e, String text) throws RuntimeException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        Result result = new Result();
        result.setStatus(ErrorConstant.UNKNOW_ERROR);
        result.setText(text);
        result.setDescription(baos.toString());
        return result;
    }

    /**
     * 抛出Error
     */
    public static Result getErrorException(Exception e, String errCode, String text) throws RuntimeException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        Result result = new Result();
        result.setStatus(ErrorConstant.UNKNOW_ERROR);
        result.setText(text);
        result.setDescription(baos.toString());
        return result;
    }


    /**
     * Exception
     * 抛出Error,错误信息重写
     */
    public static void getErrorException(String errCode, String errorMessage) {
        if (!SUCCESS.equals(errCode)) {
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * 抛出Error,错误信息重写
     */
    public static Result getErrorResult(String errCode, String errorMessage) {
        if (!SUCCESS.equals(errCode)) {
            Result result = new Result(errCode, errorMessage, "");
            result.setText(errorMessage);
            return result;
        }
        return null;
    }

    /**
     * 抛出Error,错误信息重写
     */
    public static Result getErrorResult(String errCode, String errorMessage, Object t) {
        if (!SUCCESS.equals(errCode)) {
            Result result = new Result(errCode, errorMessage, "");
            result.setText(errorMessage);
            result.setT(t);
            return result;
        }
        return null;
    }

    /**
     * 校验是否成功
     */
    public static boolean checkError(Result result) {
        if (result == null || !SUCCESS.equals(result.getStatus())) {
            return true;
        }
        return false;
    }


    /**
     * 抛出Error,错误信息重写
     */
    public static Result getSuccessResult(String successMessage) {
        Result result = new Result(SUCCESS, successMessage, "");
        result.setText(successMessage);
        return result;
    }

    /**
     * 返回操作成功
     */
    public static Result getSuccessResult(Object t) {
        Result r = new Result();
        r.setT(t);
        r.setStatus(ErrorConstant.SUCCESS);
        return r;
    }

    /**
     * 返回操作成功
     */
    public static Result getSuccessResult(Object t, String test) {
        Result r = new Result();
        r.setT(t);
        r.setStatus(ErrorConstant.SUCCESS);
        r.setText(test);
        return r;
    }



}
