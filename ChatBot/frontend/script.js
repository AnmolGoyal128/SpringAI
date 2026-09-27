/**
 * Tomato Support Chat - Frontend Script
 * Connects directly to Spring Boot backend at http://localhost:8080/api/chat
 */

document.addEventListener("DOMContentLoaded", () => {
  const chatForm = document.getElementById("chatForm");
  const userInput = document.getElementById("userInput");
  const sendBtn = document.getElementById("sendBtn");
  const messagesContainer = document.getElementById("messagesContainer");
  const typingIndicator = document.getElementById("typingIndicator");
  const clearChatBtn = document.getElementById("clearChatBtn");
  const chips = document.querySelectorAll(".chip");
  const welcomeTime = document.getElementById("welcomeTime");

  // Determine API URL (default to localhost:8080 or relative if served from same origin)
  const BACKEND_URL = window.location.origin.includes("8080")
    ? "/api/chat"
    : "http://localhost:8080/api/chat";

  // Set initial welcome time
  if (welcomeTime) {
    welcomeTime.textContent = formatTime(new Date());
  }

  // Format timestamp helper (e.g. "4:32 PM")
  function formatTime(date) {
    return date.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
  }

  // Scroll to bottom of message container
  function scrollToBottom() {
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
  }

  // Append user message to chat UI
  function appendUserMessage(text) {
    const messageEl = document.createElement("div");
    messageEl.className = "message user-message fade-in";
    messageEl.innerHTML = `
      <div class="msg-avatar">👤</div>
      <div class="msg-bubble">
        <div class="msg-text">${escapeHtml(text)}</div>
        <div class="msg-time">${formatTime(new Date())}</div>
      </div>
    `;
    // Insert before the typing indicator
    messagesContainer.insertBefore(messageEl, typingIndicator);
    scrollToBottom();
  }

  // Append bot/support message to chat UI
  function appendBotMessage(text, isError = false) {
    const messageEl = document.createElement("div");
    messageEl.className = "message bot-message fade-in";
    messageEl.innerHTML = `
      <div class="msg-avatar">🍅</div>
      <div class="msg-bubble ${isError ? "error-bubble" : ""}">
        <div class="msg-author">Tomato Support</div>
        <div class="msg-text">${escapeHtml(text)}</div>
        <div class="msg-time">${formatTime(new Date())}</div>
      </div>
    `;
    // Insert before the typing indicator
    messagesContainer.insertBefore(messageEl, typingIndicator);
    scrollToBottom();
  }

  // Show / Hide the 3-dot thinking animation
  function showThinking() {
    typingIndicator.style.display = "flex";
    messagesContainer.appendChild(typingIndicator); // Ensure it's at the bottom
    scrollToBottom();
  }

  function hideThinking() {
    typingIndicator.style.display = "none";
  }

  // Escape HTML to prevent XSS
  function escapeHtml(str) {
    const div = document.createElement("div");
    div.textContent = str;
    return div.innerHTML;
  }

  // Handle message sending to Spring Boot backend
  async function sendMessage(text) {
    const message = text.trim();
    if (!message) return;

    // 1. Show user message
    appendUserMessage(message);

    // 2. Clear input & disable form controls
    userInput.value = "";
    userInput.disabled = true;
    sendBtn.disabled = true;

    // 3. Show 3-dot thinking animation
    showThinking();

    try {
      // 4. Send request to Spring Boot backend
      const response = await fetch(BACKEND_URL, {
        method: "POST",
        headers: {
          "Content-Type": "text/plain;charset=UTF-8",
        },
        body: message,
      });

      if (!response.ok) {
        throw new Error(`Server returned HTTP ${response.status}`);
      }

      const botReply = await response.text();

      // 5. Hide thinking animation and show bot response
      hideThinking();
      appendBotMessage(botReply || "I am here to assist with your Tomato food delivery order.");

    } catch (error) {
      console.error("Tomato backend communication error:", error);
      hideThinking();
      appendBotMessage(
        "⚠️ Unable to reach Tomato Support backend. Please ensure your Spring Boot server is running on port 8080 (http://localhost:8080).",
        true
      );
    } finally {
      // 6. Re-enable form controls & focus
      userInput.disabled = false;
      sendBtn.disabled = false;
      userInput.focus();
    }
  }

  // Form submit handler
  chatForm.addEventListener("submit", (e) => {
    e.preventDefault();
    sendMessage(userInput.value);
  });

  // Suggestion chips handler
  chips.forEach((chip) => {
    chip.addEventListener("click", () => {
      const promptText = chip.getAttribute("data-prompt");
      if (promptText && !userInput.disabled) {
        userInput.value = promptText;
        userInput.focus();
      }
    });
  });

  // Clear chat button
  clearChatBtn.addEventListener("click", () => {
    if (confirm("Would you like to clear the chat history?")) {
      // Remove all messages except the welcome and typing indicator
      const messages = messagesContainer.querySelectorAll(".message:not(#typingIndicator)");
      messages.forEach((msg, idx) => {
        if (idx !== 0) { // Keep welcome message
          msg.remove();
        }
      });
      userInput.focus();
    }
  });

  // Focus input field on initial load
  userInput.focus();
});
