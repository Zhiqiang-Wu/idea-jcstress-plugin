package wzq.jcstress.plugin.configuration;

import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UastUtils;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressRunLineMarkerContributor extends RunLineMarkerContributor {

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement psiElement) {
        UElement uElement = UastUtils.getUParentForIdentifier(psiElement);
        if (uElement instanceof UClass uClass
                && uClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            AnAction[] actions = ExecutorAction.getActions(1);
            return new Info(Icons.RUN, actions, null);
        }
        return null;
    }
}
