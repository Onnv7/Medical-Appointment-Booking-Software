import mongoose from "mongoose";

const scheduleSchema = mongoose.Schema({
    date: {
        type: Date,
        unique: true,
    },
    start: {
        type: String,
    },
    end: {
        type: String,
    },
    doctorId: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor",
    }

}, { timestamps: false });

export default mongoose.model("Schedule", scheduleSchema);