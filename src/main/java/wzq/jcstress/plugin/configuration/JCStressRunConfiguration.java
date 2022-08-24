package wzq.jcstress.plugin.configuration;

import com.intellij.execution.Executor;
import com.intellij.execution.JavaRunConfigurationBase;
import com.intellij.execution.ShortenCommandLine;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.JavaRunConfigurationModule;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressRunConfiguration extends JavaRunConfigurationBase {

    private String vmParameters;
    private String programParameters;
    private String workingDirectory;
    private Map<String, String> envs = new HashMap<>();
    private boolean passParentEnvs;
    private ShortenCommandLine shortenCommandLine;
    private boolean alternativeJrePathEnabled;
    private String alternativeJrePath;

    public JCStressRunConfiguration(String name, @NotNull JavaRunConfigurationModule configurationModule, @NotNull ConfigurationFactory factory) {
        super(name, configurationModule, factory);
    }

    @Override
    public void setVMParameters(@Nullable String s) {
        this.vmParameters = s;
    }

    @Override
    public String getVMParameters() {
        return this.vmParameters;
    }

    @Override
    public boolean isAlternativeJrePathEnabled() {
        return this.alternativeJrePathEnabled;
    }

    @Override
    public void setAlternativeJrePathEnabled(boolean b) {
        this.alternativeJrePathEnabled = b;
    }

    @Override
    public @Nullable String getAlternativeJrePath() {
        return this.alternativeJrePath;
    }

    @Override
    public void setAlternativeJrePath(@Nullable String path) {
        this.alternativeJrePath = path;
    }

    @Override
    public @Nullable String getRunClass() {
        return "org.openjdk.jcstress.Main";
    }

    @Override
    public @Nullable String getPackage() {
        return null;
    }

    @Override
    public void setProgramParameters(@Nullable String s) {
        this.programParameters = s;
    }

    @Override
    public @Nullable String getProgramParameters() {
        return this.programParameters;
    }

    @Override
    public void setWorkingDirectory(@Nullable String s) {
        this.workingDirectory = s;
    }

    @Override
    public @Nullable String getWorkingDirectory() {
        return this.workingDirectory;
    }

    @Override
    public void setEnvs(@NotNull Map<String, String> map) {
        this.envs = map;
    }

    @Override
    public @NotNull Map<String, String> getEnvs() {
        return this.envs;
    }

    @Override
    public void setPassParentEnvs(boolean b) {
        this.passParentEnvs = b;
    }

    @Override
    public boolean isPassParentEnvs() {
        return this.passParentEnvs;
    }

    @Override
    public @Nullable ShortenCommandLine getShortenCommandLine() {
        return this.shortenCommandLine;
    }

    @Override
    public void setShortenCommandLine(@Nullable ShortenCommandLine shortenCommandLine) {
        this.shortenCommandLine = shortenCommandLine;
    }

    @Override
    public Collection<Module> getValidModules() {
        return null;
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new JCStressSettingsEditor();
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) {
        return new JCStressCommandLineState(executionEnvironment, this);
    }
}
