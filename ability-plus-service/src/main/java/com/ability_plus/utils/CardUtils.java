package com.ability_plus.utils;

import com.ability_plus.projectRequest.entity.ProjectProposalRecord;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.user.entity.User;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

public class CardUtils {
    public static MPJLambdaWrapper<Proposal> appendToProposalCardWrapper(MPJLambdaWrapper<Proposal> wrapper){
        wrapper
                .leftJoin(User.class,User::getId,Proposal::getCreatorId)
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .select(Proposal::getTitle)
                .select(Proposal::getOneSentenceDescription)
                .select(Proposal::getLastModifiedTime)
                .selectAs(ProjectRequest::getProjectArea,"area")
                .selectAs(Proposal::getCreatorId,"authorId")
                .selectAs(User::getFullName,"authorName")
                .selectAs(ProjectRequest::getName,"projectName")
                .select(Proposal::getLikeNum);
        ;
        return  wrapper;
    }
}
