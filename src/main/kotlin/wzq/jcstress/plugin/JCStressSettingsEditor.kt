package wzq.jcstress.plugin

import com.intellij.execution.ui.CommonJavaParametersPanel
import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent

/**
 * @author 吴志强
 */
class JCStressSettingsEditor : SettingsEditor<JCStressRunConfiguration>() {
    private val editor = CommonJavaParametersPanel()

    override fun resetEditorFrom(configuration: JCStressRunConfiguration) {
        this.editor.reset(configuration)
    }

    override fun applyEditorTo(configuration: JCStressRunConfiguration) {
        this.applyTo(configuration)
    }

    override fun createEditor(): JComponent = this.editor
}
