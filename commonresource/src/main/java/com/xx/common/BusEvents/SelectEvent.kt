package com.xx.common.BusEvents

/**
 * @author : zhijun.li on 2018/8/23
 *   email :
 *
 */
class SelectPos1(boo: Boolean){
    val isPos1  =boo
}
class SelectPos2{
    var  isPos2:Boolean=false
}
class SelectPos3{
    var  isPos3:Boolean=false

}
class SelectPos4{
    var  isPos4:Boolean=false

}
data class SelectEvent5(var upData:Int)

class CanSelectTag{
    val tagList= arrayListOf<String>()
}
class SelectedTag{
    val tagList= arrayListOf<String>()
}
