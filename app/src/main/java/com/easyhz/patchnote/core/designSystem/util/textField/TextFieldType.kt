package com.easyhz.patchnote.core.designSystem.util.textField

sealed class TextFieldType {
    data object Default: TextFieldType()
    data class DropDown(val dropDownList: List<String>, val onSelected: (String)-> Unit): TextFieldType()
}