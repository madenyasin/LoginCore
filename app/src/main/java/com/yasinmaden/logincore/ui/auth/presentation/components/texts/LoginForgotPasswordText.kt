package com.yasinmaden.logincore.ui.auth.presentation.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import com.yasinmaden.logincore.ui.auth.presentation.login.LoginContract.UiAction
import com.yasinmaden.logincore.util.Constants

@Composable
fun LoginForgotPasswordText(
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
){
    Text(
        text = buildAnnotatedString {
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = Constants.ClickableLink.ForgotPassword.tag,
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    linkInteractionListener = { onAction(UiAction.OnForgotPasswordTextClick) }
                )
            ) {
                append("Forgot your password?")
            }
        }
    )
}