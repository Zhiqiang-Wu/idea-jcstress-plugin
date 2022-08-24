package wzq.jcstress.plugin.configuration;

import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.icons.AllIcons.Actions.ProfileYellow;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressRunLineMarkerContributor extends RunLineMarkerContributor {

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement psiElement) {
        if (psiElement instanceof PsiClass psiClass && psiClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            final AnAction[] actions = new AnAction[]{ActionManager.getInstance().getAction("RunClass")};
            return new Info(ProfileYellow, new TooltipProvider(), actions);
        }
        return null;
    }

    private static class TooltipProvider implements Function<PsiElement, String> {

        @Override
        public String fun(PsiElement psiElement) {
            return "";
        }
    }
}
