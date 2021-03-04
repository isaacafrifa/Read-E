package model

object MyErrorTracker {
    /*
Main Responsibility : HELP US TRACK CONNECTION EXCEPTIONS AT RUNTIME
 */
    const val WRONG_URL_FORMAT = "Error : Wrong URL Format "
    const val CONNECTION_ERROR = "Error : Unable To Establish Connection "

    //Changed cos the error is mostly Internet Connectivity
    // public final static String IO_EROR="Error : Unable To Read ";
    const val IO_ERROR = "Error : Poor Internet Connectivity "
    const val RESPONSE_ERROR = "Error : Bad Response - "
    const val EMPTY_URL = "Error : No URL Found "
    const val PASS_ERROR = "Error: Unable To Parse"
}