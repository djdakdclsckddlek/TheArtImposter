<template>
    <div class="user-card">
        <div class="profile-picture">
            <!-- <img :src="user.profilePicture" alt="Profile Picture" /> -->
            <img :src="user.profileImage" @error="onImageError" alt="Profile Image" />
            <div v-if="user.isMe" class="user-number" :style="userNumberStyle">me</div>
        </div>
        <div class="user-info">
            <div class="nickname">{{ user.nickName }}</div>
            <div class="score">{{ formattedScore }}</div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'GameSideUserCardComponent',
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
    computed: {
        formattedScore() {
            // user.curScore가 존재하는지 확인하고, 없으면 0으로 설정
            const score = this.user.curScore || 0;
            return `${score}점`;
        },
        userNumberStyle() {
            return {
                backgroundColor: this.user.color || '#404040',
            };
        },
    },
    methods: {
        onImageError(event) {
            console.log('이미지 로드 실패:', event.target.src);
            event.target.src = require('@/assets/user/image/profile/u1.png');
        },
    },
};
</script>

<style scoped>
.user-card {
    display: flex;
    align-items: center;
    background-color: rgba(21, 21, 78, 0.676);
    border: 1px solid #7a7a7a;
    margin: 0 0 20px;
    width: 300px;
    height: 100px;
}

.profile-picture {
    position: relative;
    margin-right: 10px;
}

.profile-picture img {
    width: 75px;
    height: 75px;
    border-radius: 50%;
    margin-left: 12.5px;
}

.user-number {
    position: absolute;
    bottom: -5px;
    right: -15px;
    /* background-color: #404040; */
    color: white;
    border-radius: 50%;
    padding: 5px;
    width: 35px;
    height: 35px;
}

.user-info {
    flex-direction: column;
}

.nickname {
    text-align: left;
    font-weight: bold;
    margin-left: 15px;
    margin-bottom: 10px;
    scale: 1.2;
}

.score {
    text-align: left;
    color: #dadada;
    margin-left: 120px;
}
</style>
