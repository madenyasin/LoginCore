package com.yasinmaden.logincore.ui.auth.presentation.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract
import com.yasinmaden.logincore.util.Constants

@Composable
fun SignupHaveAnAccountText(
    onAction: (SignupContract.UiAction) -> Unit
){
    Text(
        text = buildAnnotatedString {
            append("Already have an account? ")
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = Constants.ClickableLink.SignIn.tag,
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    linkInteractionListener = { onAction(SignupContract.UiAction.OnSignInTextClick) }
                )
            ) {
                append("Sign In")
            }
        },
    )
}