import mongoose from "mongoose";

const notificationSchema = mongoose.Schema({
    title: {
        type: String
    },
    content: {
        type: String
    },
    type: {
        type: String,
    }
}, { timestamps: true });

export default mongoose.model("Notification", notificationSchema);