package self.study.sels.application.member.port.`in`

import self.study.sels.controller.dto.JoinMemberRequestDto

interface JoinUseCase {
    fun join(command: JoinMemberRequestDto)
}
