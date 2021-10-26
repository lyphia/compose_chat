package github.leavesc.compose_chat.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import github.leavesc.compose_chat.base.model.Message
import github.leavesc.compose_chat.base.model.TextMessage
import github.leavesc.compose_chat.base.model.TimeMessage
import github.leavesc.compose_chat.extend.navigate
import github.leavesc.compose_chat.model.ChatScreenState
import github.leavesc.compose_chat.model.Screen
import github.leavesc.compose_chat.utils.showToast

/**
 * @Author: leavesC
 * @Date: 2021/10/26 15:50
 * @Desc:
 * @Github：https://github.com/leavesC
 */
@Composable
fun MessageScreen(
    navController: NavController,
    chatScreenState: ChatScreenState,
    listState: LazyListState,
    contentPadding: PaddingValues
) {
    val clipboardManager = LocalClipboardManager.current
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 20.dp),
        state = listState,
        reverseLayout = true,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.Top,
    ) {
        for (message in chatScreenState.messageList) {
            item(key = message.msgId) {
                MessageItem(
                    message = message,
                    onClickSelfAvatar = {

                    },
                    onClickFriendAvatar = {
                        val messageSenderId = (it as? TextMessage)?.sender?.userId
                        if (!messageSenderId.isNullOrBlank()) {
                            navController.navigate(
                                screen = Screen.FriendProfileScreen(friendId = messageSenderId)
                            )
                        }
                    },
                    onLongPressMessage = {
                        val msg = (message as? TextMessage)?.msg
                        if (!msg.isNullOrEmpty()) {
                            clipboardManager.setText(AnnotatedString(msg))
                            showToast("已复制")
                        }
                    },
                )
            }
        }
        if (chatScreenState.showLoadMore) {
            item {
                LoadMoreMessageItem()
            }
        }
    }
}

@Composable
private fun MessageItem(
    message: Message,
    onClickSelfAvatar: () -> Unit,
    onClickFriendAvatar: (Message) -> Unit,
    onLongPressMessage: (Message) -> Unit,
) {
    val unit = when (message) {
        is TextMessage -> {
            when (message) {
                is TextMessage.SelfTextMessage -> {
                    SelfTextMessageItem(
                        textMessage = message,
                        onClickSelfAvatar = onClickSelfAvatar,
                        onLongPressMessage = onLongPressMessage,
                    )
                }
                is TextMessage.FriendTextMessage -> {
                    FriendTextMessageItem(
                        textMessage = message,
                        onClickFriendAvatar = onClickFriendAvatar,
                        onLongPressMessage = onLongPressMessage,
                    )
                }
            }
        }
        is TimeMessage -> {
            TimeMessageItem(timeMessage = message)
        }
    }
}