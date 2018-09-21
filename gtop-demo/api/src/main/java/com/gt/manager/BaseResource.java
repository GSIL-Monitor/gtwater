package com.gt.manager;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: rest接口基类 </p>
 * <p>Author:jmzhang/张际明, 16/09/20</p>
 */
public class BaseResource {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T extends Object> void validator(T entity) throws ResourceException {
        List<String> list = new ArrayList<String>();

        Set<ConstraintViolation<T>> validators = validator.validate(entity);
        for (ConstraintViolation<T> constraintViolation : validators) {
            list.add(constraintViolation.getMessage());
        }

        if (0 < list.size()) {
            throw new ResourceException(list.toString());
        }
    }

    /**
     * 验证所有参数不为空
     * @param params
     * @return
     */
    public static void validParamsNotNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new ServiceException(Result.BAD_PARAMETERS.getCode(), String.format(
                        Result.BAD_PARAMETERS.getMessage(), param));
            }
        }
    }
}
