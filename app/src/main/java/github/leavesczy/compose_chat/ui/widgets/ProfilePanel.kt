package github.leavesczy.compose_chat.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import github.leavesczy.compose_chat.extend.clickableNoRipple
import github.leavesczy.compose_chat.extend.scrim
import github.leavesczy.compose_chat.ui.preview.PreviewImageActivity

/**
 * @Author: leavesCZY
 * @Date: 2021/6/23 21:56
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
@Composable
fun ProfilePanel(
    title: String,
    subtitle: String,
    introduction: String,
    avatarUrl: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    ConstraintLayout(modifier = Modifier) {
        val (titleRef, subtitleRef, introductionRef, backgroundRef, avatarRef, contentRef) = createRefs()
        BezierImage(modifier = Modifier
            .constrainAs(ref = backgroundRef) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(anchor = parent.top)
            }
            .aspectRatio(ratio = 5f / 4f)
            .zIndex(zIndex = -100f)
            .scrim(color = Color(0x51444B53)),
            data = avatarUrl
        )
        BouncyImage(
            modifier = Modifier
                .constrainAs(ref = avatarRef) {
                    linkTo(
                        top = backgroundRef.top,
                        bottom = backgroundRef.bottom,
                        bias = 0.3f
                    )
                    linkTo(start = backgroundRef.start, end = backgroundRef.end)
                }
                .size(size = 110.dp)
                .clickableNoRipple {
                    if (avatarUrl.isNotBlank()) {
                        PreviewImageActivity.navTo(context = context, imagePath = avatarUrl)
                    }
                },
            data = avatarUrl
        )
        Text(
            modifier = Modifier
                .constrainAs(ref = titleRef) {
                    linkTo(start = backgroundRef.start, end = backgroundRef.end)
                    top.linkTo(anchor = avatarRef.bottom, margin = 10.dp)
                }
                .padding(horizontal = 10.dp),
            text = title,
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .constrainAs(ref = subtitleRef) {
                    linkTo(start = backgroundRef.start, end = backgroundRef.end)
                    top.linkTo(anchor = titleRef.bottom, margin = 10.dp)
                }
                .padding(horizontal = 10.dp),
            text = subtitle,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .constrainAs(ref = introductionRef) {
                    linkTo(start = backgroundRef.start, end = backgroundRef.end)
                    top.linkTo(anchor = subtitleRef.bottom, margin = 10.dp)
                }
                .padding(horizontal = 10.dp),
            text = introduction,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Box(
            modifier = Modifier
                .constrainAs(ref = contentRef) {
                    linkTo(start = backgroundRef.start, end = backgroundRef.end)
                    top.linkTo(anchor = introductionRef.bottom, margin = 60.dp)
                }
                .zIndex(zIndex = 1f)
        ) {
            content()
        }
    }
}