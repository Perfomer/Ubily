package com.vmedia.feature.auth.data.browser.util

internal class JsScriptBuilder(
    private val evaluator: (script: String) -> Unit
) {

    private var script = StringBuilder()

    fun evaluate() {
        evaluator.invoke(script.toString())
        script.clear()
    }

    private fun addOperation(operation: String) {
        script.append("\n").append(operation)
    }

    fun fill(fieldId: String, value: String): JsScriptBuilder {
        addOperation("document.getElementById('$fieldId').value ='$value';")
        return this
    }

    fun click(name: String): JsScriptBuilder {
        addOperation("document.getElementsByName('$name')[0].click();")
        return this
    }

    fun setSelectIndex(selectId: String, index: Int): JsScriptBuilder {
        addOperation("document.getElementById('$selectId').selectedIndex = $index;")
        return this
    }

    fun setSelectValue(selectId: String, value: String): JsScriptBuilder {
        addOperation("document.getElementById('$selectId').value = '$value';")
        return this
    }

    fun sleep(millis: Long): JsScriptBuilder {
        addOperation("sleep($millis);")
        return this
    }

    fun queryPageSource(): JsScriptBuilder {
        addOperation("window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
        return this
    }

    fun printPublisherDescription(): JsScriptBuilder {
        addOperation("console.log(u3d.publisher.overview);")
        return this
    }

}