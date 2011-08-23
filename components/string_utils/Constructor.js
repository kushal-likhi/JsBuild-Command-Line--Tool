function(){
    String.prototype.startsWith = jsbuildRuntimeBasePackage.string_utils.enrichers.startsWith;
    String.prototype.endsWith = jsbuildRuntimeBasePackage.string_utils.enrichers.endsWith;
    String.prototype.toArray = jsbuildRuntimeBasePackage.string_utils.enrichers.toArray;
    String.prototype.toInteger = jsbuildRuntimeBasePackage.string_utils.enrichers.toInteger;
    String.prototype.toFloat = jsbuildRuntimeBasePackage.string_utils.enrichers.toFloat;
    String.prototype.toBoolean = jsbuildRuntimeBasePackage.string_utils.enrichers.toBoolean;
    String.prototype.size = jsbuildRuntimeBasePackage.string_utils.enrichers.getSize;
    String.prototype.reverse = jsbuildRuntimeBasePackage.string_utils.enrichers.reverse;
    String.prototype.readLines = jsbuildRuntimeBasePackage.string_utils.enrichers.readLines;
    String.prototype.repeate = jsbuildRuntimeBasePackage.string_utils.enrichers.repeate;
    String.prototype.replaceAll = jsbuildRuntimeBasePackage.string_utils.enrichers.replaceAll;
    String.prototype.isBlank = jsbuildRuntimeBasePackage.string_utils.enrichers.isBlank;
    String.prototype.isEmail = jsbuildRuntimeBasePackage.string_utils.enrichers.isEmail;
    String.prototype.isURL = jsbuildRuntimeBasePackage.string_utils.enrichers.isURL;
    String.prototype.isDate = jsbuildRuntimeBasePackage.string_utils.enrichers.isDate;
    String.prototype.isDateISO = jsbuildRuntimeBasePackage.string_utils.enrichers.isDateISO;
    String.prototype.isDateDE = jsbuildRuntimeBasePackage.string_utils.enrichers.isDateDE;
    String.prototype.isNumber = jsbuildRuntimeBasePackage.string_utils.enrichers.isNumber;
    String.prototype.isNumberDE = jsbuildRuntimeBasePackage.string_utils.enrichers.isNumberDE;
    String.prototype.isDigits = jsbuildRuntimeBasePackage.string_utils.enrichers.isDigits;
    String.prototype.isCreditCard = jsbuildRuntimeBasePackage.string_utils.enrichers.isCreditCard;
    String.prototype.contains = jsbuildRuntimeBasePackage.string_utils.enrichers.contains;
    String.prototype.parseDate = jsbuildRuntimeBasePackage.string_utils.enrichers.parseDate;
}