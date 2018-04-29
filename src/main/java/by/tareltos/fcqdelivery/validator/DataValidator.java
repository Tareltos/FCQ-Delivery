package by.tareltos.fcqdelivery.validator;

import sun.util.resources.cldr.rm.CalendarData_rm_CH;

public class DataValidator {

    public static boolean validateEmail(String email) {
        return (email == null | email.isEmpty()) ? false : true;
    }

    public static boolean validatePassword(String pass) {
        return (pass == null | pass.length() < 5) ? false : true;
    }

    public static boolean validateName(String name) {
        return (name == null | name.isEmpty()) ? false : true;
    }

    public static boolean validatePhone(String phone) {
        return (null == phone | phone.isEmpty() | phone.length() != 13) ? false : true;
    }

    public static boolean validateCarNumber(String carNumber) {
        return (null == carNumber | carNumber.length() != 8) ? false : true;
    }

    public static boolean validateCarProducer(String carProducer) {
        return (null == carProducer) ? false : true;
    }

    public static boolean validateCarModel(String carModel) {
        return (null == carModel | carModel.isEmpty()) ? false : true;
    }

    public static boolean validateTax(double tax) {
        return tax >= 0;
    }

    public static boolean validateCargo(int maxCargo) {
        return maxCargo >= 0;
    }

    public static boolean validateStatus(String status) {
        if ("active".equals(status)) {
            return true;
        }
        if ("blocked".equals(status)) {
            return true;
        }
        return false;
    }

    public static boolean validateApplicationId(String id) {
        return null == id ? false : true;
    }

    public static boolean validateDistance(String distance) {
        return Integer.parseInt(distance) > 0;
    }

    public static boolean validateReasonOfCancel(String reason) {
        if (null == reason) {
            return false;
        }
        if (reason.equals(" ")) {
            return false;
        }
        return true;
    }

    public static boolean validateStartPoint(String startPoint) {
        return (startPoint == null | startPoint.isEmpty()) ? false : true;
    }

    public static boolean validateFinishPoint(String finishPoint) {
        return (finishPoint == null | finishPoint.isEmpty()) ? false : true;
    }

    public static boolean validateDate(String date) {
        return (date == null | date.isEmpty()) ? false : true;
    }

    public static boolean validateComment(String comment) {
        return (comment == null | comment.isEmpty()) ? false : true;
    }

    public static boolean validateCardNumber(String cardNumber) {
        if (cardNumber.length() == 16) {
            return true;
        }
        return false;
    }

    public static boolean validateExpirationMonth(String expMonth) {
        if (Integer.parseInt(expMonth) > 0 & Integer.parseInt(expMonth) < 13) {
            return true;
        }
        return false;
    }

    public static boolean validateExpirationYear(String expYear) {
        if (Integer.parseInt(expYear) > 17 & Integer.parseInt(expYear) < 100) {
            return true;
        }
        return false;
    }

    public static boolean validateOwner(String owner) {
        if (owner.split(" ").length == 2) {
            return true;
        }
        return false;
    }

    public static boolean validateCsv(String csv) {
        if (Integer.parseInt(csv) > 99 & Integer.parseInt(csv) < 1000) {
            return true;
        }
        return false;
    }
}
