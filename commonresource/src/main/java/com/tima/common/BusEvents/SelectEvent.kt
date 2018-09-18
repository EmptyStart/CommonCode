package com.tima.common.BusEvents

/**
 * @author : zhijun.li on 2018/8/23
 *   email : zhijun.li@timanetworks.com
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
class SelectPos5{
    var  isPos5:Boolean=false

}

class CanSelectTag{
    val tagList= arrayListOf<String>()
}
class SelectedTag{
    val tagList= arrayListOf<String>()
}
