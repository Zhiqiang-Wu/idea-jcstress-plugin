package wzq.jcstress.plugin.configuration;

import javax.swing.JComponent;

import com.intellij.execution.ui.CommonJavaParametersPanel;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressSettingsEditor extends SettingsEditor<JCStressRunConfiguration> {

    private final CommonJavaParametersPanel editor = new CommonJavaParametersPanel();

    @Override
    protected void resetEditorFrom(@NotNull JCStressRunConfiguration runConfiguration) {
        this.editor.reset(runConfiguration);
    }

    @Override
    protected void applyEditorTo(@NotNull JCStressRunConfiguration runConfiguration) {
        this.editor.applyTo(runConfiguration);
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return this.editor;
    }
}
