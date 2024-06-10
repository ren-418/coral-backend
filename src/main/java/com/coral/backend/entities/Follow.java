package com.coral.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "followings")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private InvestorUser follower;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private InvestorUser followed;

    public long getFollowId() {
        return followId;
    }
    public void setFollowId(long followId) {
        this.followId = followId;
    }
    public InvestorUser getFollower() {
        return follower;
    }
    public void setFollower(InvestorUser follower) {
        this.follower = follower;
    }
    public InvestorUser getFollowed() {
        return followed;
    }
    public void setFollowed(InvestorUser followed) {
        this.followed = followed;
    }
}
