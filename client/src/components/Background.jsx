import React, { useEffect, useState } from 'react';
import '../App.css';

const Background = () => {
    const [leaves, setLeaves] = useState([]);

    useEffect(() => {
        const leafCount = 15;
        const newLeaves = [];

        for (let i = 0; i < leafCount; i++) {
            const size = Math.random() * 40 + 20; // Random size
            newLeaves.push({
                id: i,
                left: `${Math.random() * 100}vw`,
                top: `${Math.random() * 100}vh`,
                size: `${size}px`,
                delay: `${Math.random() * 5}s`,
                duration: `${Math.random() * 15 + 5}s`
            });
        }
        setLeaves(newLeaves);
    }, []);

    return (
        <div>
            {leaves.map((leaf) => (
                <div
                    key={leaf.id}
                    className="leaf"
                    style={{
                        left: leaf.left,
                        top: leaf.top,
                        width: leaf.size,
                        height: leaf.size,
                        animationDelay: leaf.delay,
                        animationDuration: leaf.duration
                    }}
                />
            ))}
        </div>
    );
};

export default Background;